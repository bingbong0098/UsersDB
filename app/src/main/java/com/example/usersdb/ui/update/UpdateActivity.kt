package com.example.usersdb.ui.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usersdb.databinding.ActivityUpdateBinding
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class UpdateActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var userEntity: UserEntity


    lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var userId = 0

        intent.extras?.let {
            userId = it.getInt("bundle_user_id")
        }

        binding.apply {
            nameEdt.setText(repository.getUser(userId).name.toString())
            familyEdt.setText(repository.getUser(userId).family.toString())
            ageEdt.setText(repository.getUser(userId).age.toString())
            nationalNumberEdt.setText(repository.getUser(userId).nationalNumber.toString())

            updateBtn.setOnClickListener {

                userEntity.apply {
                    id = userId
                    name = nameEdt.text.toString()
                    family = familyEdt.text.toString()
                    age = ageEdt.text.toString()
                    nationalNumber = nationalNumberEdt.text.toString()

                }
                repository.updateUser(userEntity)
                startActivity(Intent(this@UpdateActivity,MainActivity::class.java))
                finish()
            }

            deleteBtn.setOnClickListener {
                userEntity.id = userId
//                repository.deletetUser(repository.getUser(userId))
                repository.deleteUser(userEntity)
                startActivity(Intent(this@UpdateActivity,MainActivity::class.java))
                finish()

            }
        }


    }
}