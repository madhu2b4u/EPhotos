package com.ephotos.photo.domain

import androidx.lifecycle.LiveData
import com.ephotos.common.Result
import com.ephotos.photo.data.remote.models.PhotoResponse

interface PhotoUseCase {
    suspend fun getPhotoInfo(
        id: String,
        mustFetchFromNetwork: Boolean
    ): LiveData<Result<PhotoResponse>>

}