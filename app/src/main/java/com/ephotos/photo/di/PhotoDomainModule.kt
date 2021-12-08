package com.ephotos.photo.di

import com.ephotos.photo.data.repository.PhotoRepository
import com.ephotos.photo.data.repository.PhotoRepositoryImpl
import com.ephotos.photo.domain.PhotoUseCase
import com.ephotos.photo.domain.PhotoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoDomainModule {
    @Binds
    abstract fun bindsRepository(
        repoImpl: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    abstract fun bindsPhotoUseCase(
        mPhotoUseCase: PhotoUseCaseImpl
    ): PhotoUseCase
}