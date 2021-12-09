package com.ephotos.database.source

import com.ephotos.photo.data.remote.models.PhotoResponse


interface LocalDataSource {
    suspend fun getPhoto(): PhotoResponse?
    suspend fun savePhoto(photo: PhotoResponse)
}