package com.example.composecampgroup4.di

import android.content.Context
import androidx.room.Room
import com.example.composecampgroup4.presentation.core.utils.Constants
import com.example.composecampgroup4.data.local.database.JarDatabase
import com.example.composecampgroup4.data.network.api.ApiFactory
import com.example.composecampgroup4.domain.JarLinkValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): JarDatabase {
        return Room.databaseBuilder(
            context,
            JarDatabase::class.java,
            Constants.JAR_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideJarDao(database: JarDatabase) = database.getJarDao()

    @Provides
    @Singleton
    fun provideApiFactory() = ApiFactory

    @Provides
    fun provideJarLinkValidator() = JarLinkValidator()
}