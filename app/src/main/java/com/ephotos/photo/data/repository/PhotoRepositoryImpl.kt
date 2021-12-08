package com.ephotos.photo.data.repository


import com.ephotos.photo.data.remote.source.PhotoRemoteDataSource
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository