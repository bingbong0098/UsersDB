package com.example.usersdb.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class UserDatabase :RoomDatabase() {
    abstract fun userDao() : UserDao
}