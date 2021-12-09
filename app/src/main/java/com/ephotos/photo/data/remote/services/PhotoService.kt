package com.ephotos.photo.data.remote.services

import com.ephotos.photo.data.remote.models.PhotoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoService {
    @GET("{id}/info")
    fun getPhotoInfoAsync(@Path("id") id: String): Deferred<Response<PhotoResponse>>
}