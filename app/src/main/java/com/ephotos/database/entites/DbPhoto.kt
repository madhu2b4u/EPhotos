package com.pulka.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_photo")
data class DbPhoto(
    @PrimaryKey val id: Int,
    val photo: String
)
