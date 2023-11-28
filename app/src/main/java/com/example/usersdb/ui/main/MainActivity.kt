package com.example.usersdb.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersdb.databinding.ActivityMainBinding
import com.example.usersdb.db.UserDao
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.addUser.AddUserActivity
import com.example.usersdb.ui.main.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UsersAdapter.userClickListiner {

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var usersAdapter: UsersAdapter

    @Inject
    lateinit var userEntity: UserEntity


    @Inject
    lateinit var userDao: UserDao

//    val list = mutableListOf<Int>()

     var deleteList: List<Int?> = mutableListOf()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            usersRv.apply {

                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = usersAdapter
            }
            usersAdapter.differ.submitList(userDao.getAllUsers())

            addBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
            }

            deleteBtn.setOnClickListener {

                repository.deleteItems(deleteList)
            }
        }
    }
    override fun users(users: MutableList<Int?>) {
//        list.add(userEntity.id!!)
        deleteList = users
        doSomethingWithDataList()
    }


    private fun doSomethingWithDataList() {
        if (deleteList != null) {


            for (data in deleteList) {
                Log.d("YourActivity", "Data: $data")
            }
        }
    }
}