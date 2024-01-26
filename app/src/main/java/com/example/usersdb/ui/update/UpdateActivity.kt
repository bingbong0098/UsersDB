package com.example.usersdb.ui.update

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.viewModels
import com.example.usersdb.databinding.ActivityUpdateBinding
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.main.MainActivity
import com.example.usersdb.viewmodel.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class UpdateActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var userEntity: UserEntity

    val viewModel: UpdateViewModel by viewModels()

    val calendar = Calendar.getInstance()

    var dateText: String = "${calendar.get(Calendar.YEAR)} / ${calendar.get(Calendar.MONTH) + 1} / ${calendar.get(Calendar.DAY_OF_MONTH)}"

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


            dateBtn.setOnClickListener {
                DatePickerDialog(
                    this@UpdateActivity,
                    this@UpdateActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

            }

            viewModel.getUser(userId)

            viewModel.userItem.observe(this@UpdateActivity){
                nameEdt.setText(it.name)
                familyEdt.setText(it.family)
                ageEdt.setText(it.age)
                nationalNumberEdt.setText(it.nationalNumber)
                dateTxt.text = it.date

            }

            updateBtn.setOnClickListener {

                userEntity.apply {
                    id = userId
                    name = nameEdt.text.toString()
                    family = familyEdt.text.toString()
                    age = ageEdt.text.toString()
                    nationalNumber = nationalNumberEdt.text.toString()
                    date = dateText

                }
                viewModel.updateUser(userEntity)
                startActivity(Intent(this@UpdateActivity,MainActivity::class.java))
                finish()
            }

            deleteBtn.setOnClickListener {
                userEntity.id = userId
//                repository.deletetUser(repository.getUser(userId))
                viewModel.deleteUser(userEntity)
                startActivity(Intent(this@UpdateActivity,MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateText = "$year / ${month + 1} / $dayOfMonth"
        binding.dateTxt.text = dateText
    }
}