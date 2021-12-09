package com.ephotos.photo.data.repository


import androidx.lifecycle.liveData
import com.ephotos.common.Result
import com.ephotos.database.source.LocalDataSource
import com.ephotos.photo.data.remote.models.PhotoResponse
import com.ephotos.photo.data.remote.source.PhotoRemoteDataSource
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource,
    private val localDataSource: LocalDataSource
) : PhotoRepository {
    override suspend fun getPhotoInfo(id: String, mustFetchFromNetwork: Boolean) = liveData {
        emit(Result.loading())
        try {
            var photo: PhotoResponse? = null
            if (!mustFetchFromNetwork)
                photo = localDataSource.getPhoto()

            if (photo == null) {
                photo = remoteDataSource.getPhotoInfo(id)
                localDataSource.savePhoto(photo)
            }
            emit(Result.success(photo))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: "", null))
        }
    }
}