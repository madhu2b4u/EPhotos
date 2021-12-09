package com.ephotos.photo.data.repository


import androidx.lifecycle.liveData
import com.ephotos.common.Result
import com.ephotos.photo.data.remote.source.PhotoRemoteDataSource
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {
    override suspend fun getPhotoInfo(id: String) = liveData {
        emit(Result.loading())
        try {
            val response = remoteDataSource.getPhotoInfo(id)
            emit(Result.success(response))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: "", null))
        }
    }
}