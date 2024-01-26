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
class MainViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    fun allUsers() = repository.getAllUser()


    fun deleteItemUser(users: List<Int?>) = viewModelScope.launch {
        repository.deleteItems(users)
    }
}