package com.ephotos.photo.data.remote.source

import com.ephotos.di.qualifiers.IO
import com.ephotos.photo.data.remote.services.PhotoService
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PhotoRemoteDataSourceImpl @Inject constructor(
    private val service: PhotoService,
    @IO private val context: CoroutineContext
) : PhotoRemoteDataSource {
    override suspend fun getPhotoInfo(id: String) = withContext(context) {
        val response = service.getPhotoInfoAsync(id).await()
        if (response.isSuccessful)
            response.body() ?: throw Exception("no response")
        else
            throw Exception("" + response.code())
    }
}
