package com.example.usersdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    val userItem = MutableLiveData<UserEntity>()

    fun getUser(id:Int) = viewModelScope.launch {

        val user = repository.getUser(id)
        userItem.postValue(user)

    }

    fun updateUser(user : UserEntity) = viewModelScope.launch {
        repository.updateUser(user)
    }


    fun deleteUser(user : UserEntity) = viewModelScope.launch {
        repository.deleteUser(user)
    }

}