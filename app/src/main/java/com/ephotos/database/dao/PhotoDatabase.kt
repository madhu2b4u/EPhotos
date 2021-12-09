package com.ephotos.database.dao

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pulka.database.entites.*

@Database(
    entities = [DbPhoto::class],
    version = 1,
    exportSchema = false
)
abstract class PhotoDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "media.db"

        @Volatile
        private var INSTANCE: PhotoDatabase? = null

        fun getInstance(@NonNull context: Context): PhotoDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            PhotoDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getPhotoDao(): PhotoDao

}