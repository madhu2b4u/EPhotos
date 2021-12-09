package com.ephotos.photo.presentation.viewmodel

import androidx.lifecycle.*
import com.ephotos.common.*
import com.ephotos.photo.data.remote.models.PhotoResponse
import com.ephotos.photo.domain.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val mPhotoUseCase: PhotoUseCase
) : ViewModel(), LifecycleObserver {

    val photoResult: LiveData<PhotoResponse> get() = _photoResult
    private val _photoResult = MediatorLiveData<PhotoResponse>()

    val showLoader: LiveData<Unit> get() = _showLoader
    private val _showLoader = SingleLiveEvent<Unit>()

    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()

    fun getPhotoInfo(id: String) {
        viewModelScope.launchSafely {
            withContext(dispatcher.main()) {
                _photoResult.addSource(mPhotoUseCase.getPhotoInfo(id)) {
                    photoResult(it)
                }
            }
        }
    }


    private fun photoResult(it: Result<PhotoResponse>) {
        when (it.status) {
            Status.LOADING -> _showLoader.call()
            Status.ERROR -> {
                it.message
            }
            Status.SUCCESS -> {
                _photoResult.postValue(it.data!!)
            }
        }
    }

}