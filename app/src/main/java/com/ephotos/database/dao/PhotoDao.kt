package com.ephotos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pulka.database.entites.*

@Dao
abstract class PhotoDao {
    @Query("SELECT * FROM db_photo")
    abstract suspend fun getPhotoFromDatabase(): DbPhoto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun savePhotoToDatabase(photo: DbPhoto)

}