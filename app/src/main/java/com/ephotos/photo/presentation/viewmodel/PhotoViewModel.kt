package com.ephotos.photo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ephotos.photo.domain.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val mPhotoUseCase: PhotoUseCase
) : ViewModel()