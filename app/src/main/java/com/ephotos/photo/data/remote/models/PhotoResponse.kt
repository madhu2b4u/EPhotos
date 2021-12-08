package com.ephotos.photo.data.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PhotoResponse(
    @Expose @SerializedName("author")
    val author: String,
    @Expose @SerializedName("download_url")
    val downloadUrl: String,
    @Expose @SerializedName("height")
    val height: Int,
    @Expose @SerializedName("id")
    val id: String,
    @Expose @SerializedName("url")
    val url: String,
    @Expose @SerializedName("width")
    val width: Int
) : Parcelable