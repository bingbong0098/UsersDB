package com.example.usersdb.ui.addUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usersdb.R
import com.example.usersdb.databinding.ActivityAddUserBinding
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class AddUserActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var userEntity: UserEntity


    lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {


            saveBtn.setOnClickListener {
                userEntity.name = nameEdt.text.toString()
                userEntity.family = familyEdt.text.toString()
                userEntity.age = ageEdt.text.toString()
                userEntity.nationalNumber = nationalNumberEdt.text.toString()
                repository.addUser(userEntity)

                startActivity(Intent(this@AddUserActivity,MainActivity::class.java))

                finish()

            }

        }
    }
}