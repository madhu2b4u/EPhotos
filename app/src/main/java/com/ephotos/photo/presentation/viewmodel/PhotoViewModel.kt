package com.ephotos.photo.presentation.viewmodel

import androidx.lifecycle.*
import com.ephotos.R
import com.ephotos.common.*
import com.ephotos.photo.data.remote.models.PhotoResponse
import com.ephotos.photo.domain.PhotoUseCase
import com.ephotos.snackbar.Message
import com.ephotos.snackbar.SnackbarAlertType
import com.ephotos.snackbar.SnackbarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val mPhotoUseCase: PhotoUseCase
) : ViewModel(), LifecycleObserver {

    val photoResult: LiveData<PhotoResponse> get() = _photoResult
    private val _photoResult = MediatorLiveData<PhotoResponse>()

    val showSnackbar: LiveData<SnackbarData> get() = _showSnackbar
    private val _showSnackbar = SingleLiveEvent<SnackbarData>()

    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()

    fun getPhotoInfo(id: String, mustFetchFromNetwork: Boolean) {
        viewModelScope.launchSafely {
            withContext(dispatcher.main()) {
                _photoResult.addSource(mPhotoUseCase.getPhotoInfo(id, mustFetchFromNetwork)) {
                    photoResult(it)
                }
            }
        }
    }


    private fun photoResult(it: Result<PhotoResponse>) {
        when (it.status) {
            Status.ERROR -> {
                handleError(it.message)
            }
            Status.SUCCESS -> {
                _photoResult.postValue(it.data!!)
            }
        }
    }

    private fun handleError(errorCode: String?) {
        when (errorCode) {
            "404" -> showErrorDuringFetchingData(R.string.noImage)
            "400" -> showNetworkErrorSnackBar()
            else -> showErrorDuringFetchingData(R.string.somethingWentWrong)
        }
    }

    fun showNetworkErrorSnackBar() {
        _showSnackbar.value = SnackbarData.Builder()
            .setAlertType(SnackbarAlertType.MINOR_ALERT)
            .setMessage(Message.AsResource(R.string.network_error_message))
            .build()
    }

    private fun showErrorDuringFetchingData(message: Int) {
        _showSnackbar.value = SnackbarData.Builder()
            .setAlertType(SnackbarAlertType.MINOR_ALERT)
            .setMessage(Message.AsResource(message))
            .build()
    }

}