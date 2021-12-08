package com.ephotos.photo.di

import com.ephotos.photo.data.remote.services.PhotoService
import com.ephotos.photo.data.remote.source.PhotoRemoteDataSource
import com.ephotos.photo.data.remote.source.PhotoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [PhotoRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class PhotoRemoteModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: PhotoRemoteDataSourceImpl
        ): PhotoRemoteDataSource
    }

    @Provides
    fun providesPhotoService(retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)

}