package com.ephotos.photo.data.repository

import androidx.lifecycle.LiveData
import com.ephotos.common.Result
import com.ephotos.photo.data.remote.models.PhotoResponse


interface PhotoRepository {
    suspend fun getPhotoInfo(
        id: String,
        mustFetchFromNetwork: Boolean
    ): LiveData<Result<PhotoResponse>>

}