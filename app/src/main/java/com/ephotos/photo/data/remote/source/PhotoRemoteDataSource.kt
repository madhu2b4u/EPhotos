package com.ephotos.photo.data.remote.source

import com.ephotos.photo.data.remote.models.PhotoResponse

interface PhotoRemoteDataSource {
    suspend fun getPhotoInfo(id: String): PhotoResponse
}