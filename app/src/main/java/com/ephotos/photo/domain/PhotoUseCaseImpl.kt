package com.ephotos.photo.domain

import com.ephotos.photo.data.repository.PhotoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoUseCaseImpl @Inject constructor(private val mPhotoRepository: PhotoRepository) :
    PhotoUseCase {
    override suspend fun getPhotoInfo(id: String) = mPhotoRepository.getPhotoInfo(id)
}
