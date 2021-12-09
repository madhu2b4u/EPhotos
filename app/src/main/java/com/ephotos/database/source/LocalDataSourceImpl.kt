package com.ephotos.database.source

import com.ephotos.database.dao.PhotoDao
import com.ephotos.database.mapper.PhotoMapper
import com.ephotos.di.qualifiers.IO
import com.ephotos.photo.data.remote.models.PhotoResponse
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalDataSourceImpl @Inject constructor(
    private val pulkaDao: PhotoDao,
    private val photoMapper: PhotoMapper,
    @IO private val context: CoroutineContext
) : LocalDataSource {

    override suspend fun savePhoto(photo: PhotoResponse) = withContext(context) {
        val photoMapper = photoMapper.from(photo)
        pulkaDao.savePhotoToDatabase(photoMapper)
    }


    override suspend fun getPhoto() = withContext(context) {
        val photoEntity = pulkaDao.getPhotoFromDatabase()
        if (photoEntity != null)
            photoMapper.to(photoEntity)
        else
            null
    }


}