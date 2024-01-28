package com.example.usersdb.ui.addUser

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import com.example.usersdb.R
import com.example.usersdb.databinding.ActivityAddUserBinding
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.main.MainActivity
import com.example.usersdb.viewmodel.AddUserViewModel
import com.example.usersdb.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class AddUserActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    val viewModel: AddUserViewModel by viewModels()

    @Inject
    lateinit var userEntity: UserEntity

    val calendar = Calendar.getInstance()

    var dateText: String = "${calendar.get(Calendar.YEAR)} / ${calendar.get(Calendar.MONTH) + 1} / ${calendar.get(Calendar.DAY_OF_MONTH)}"


    lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {

            dateTxt.text = dateText

            dateBtn.setOnClickListener {
                DatePickerDialog(
                    this@AddUserActivity,
                    this@AddUserActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

                dateTxt.text = dateText
            }

            saveBtn.setOnClickListener {
                userEntity.name = nameEdt.text.toString()
                userEntity.family = familyEdt.text.toString()
                userEntity.age = ageEdt.text.toString()
                userEntity.nationalNumber = nationalNumberEdt.text.toString()
                userEntity.date = dateText
                viewModel.setUserItem(userEntity)

                finish()

            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateText = "$year / ${month + 1} / $dayOfMonth"
        binding.dateTxt.text = dateText
    }
}