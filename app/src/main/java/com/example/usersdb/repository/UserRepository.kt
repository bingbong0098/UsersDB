package com.example.usersdb.repository

import com.example.usersdb.db.UserDao
import com.example.usersdb.db.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao)  {
    fun addUser(userEntity: UserEntity) = dao.addUser(userEntity)
    fun getAllUser() = dao.getAllUsers()

    fun getUser(id: Int) = dao.getUser(id)
    fun deleteUser(userEntity: UserEntity) = dao.deleteUser(userEntity)
    fun updateUser(userEntity: UserEntity) = dao.updateUser(userEntity)
    fun deleteItems(list:List<Int?>) = dao.deleteUsersByIds(list)
}