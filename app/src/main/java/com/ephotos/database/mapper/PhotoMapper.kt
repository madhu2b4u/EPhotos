package com.ephotos.database.mapper

import com.ephotos.photo.data.remote.models.PhotoResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pulka.database.entites.DbPhoto
import com.pulka.database.mapper.Mapper
import javax.inject.Inject

class PhotoMapper @Inject constructor(val gson: Gson) :
    Mapper<DbPhoto, PhotoResponse> {
    override fun from(e: PhotoResponse) = DbPhoto(1, gson.toJson(e))
    override fun to(t: DbPhoto): PhotoResponse {
        return gson.fromJson(
            t.photo,
            TypeToken.getParameterized(PhotoResponse::class.java, PhotoResponse::class.java).type
        )
    }
}