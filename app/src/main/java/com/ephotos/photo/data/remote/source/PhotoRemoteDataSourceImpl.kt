package com.ephotos.photo.data.remote.source

import com.ephotos.di.qualifiers.IO
import com.ephotos.photo.data.remote.services.PhotoService
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PhotoRemoteDataSourceImpl @Inject constructor(
    private val service: PhotoService,
    @IO private val context: CoroutineContext
) : PhotoRemoteDataSource
