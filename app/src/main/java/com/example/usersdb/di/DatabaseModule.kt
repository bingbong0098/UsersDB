package com.example.usersdb.di

import android.content.Context
import androidx.room.Room
import com.example.usersdb.utils.NOTE_DATABASE
import com.example.usersdb.db.UserDatabase
import com.example.usersdb.db.UserEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, UserDatabase::class.java, NOTE_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: UserDatabase) = db.userDao()

    @Provides
    fun provideEntity() = UserEntity()


}