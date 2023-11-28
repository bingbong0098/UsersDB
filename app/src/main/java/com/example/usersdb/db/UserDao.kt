package com.example.usersdb.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)


    @Query("SELECT * FROM TABLE_NAME")
    fun getAllUsers() : LiveData<MutableList<UserEntity>>


    @Query("SELECT * FROM TABLE_NAME WHERE id LIKE :id")
    fun getUser(id: Int): UserEntity

    @Query("DELETE FROM TABLE_NAME WHERE id IN (:userIds)")
    fun deleteUsersByIds(userIds: List<Int?>)


}