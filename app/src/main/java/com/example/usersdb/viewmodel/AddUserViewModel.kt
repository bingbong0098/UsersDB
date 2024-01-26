package com.example.usersdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    fun setUserItem(user:UserEntity){
        repository.addUser(user)
    }

}