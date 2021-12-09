package com.ephotos.di

import android.app.Application
import com.ephotos.database.dao.PhotoDatabase
import com.ephotos.database.source.LocalDataSource
import com.ephotos.database.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [LocalModule.Binders::class])
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: LocalDataSourceImpl
        ): LocalDataSource
    }

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = PhotoDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesUserDao(
        data: PhotoDatabase
    ) = data.getPhotoDao()
}