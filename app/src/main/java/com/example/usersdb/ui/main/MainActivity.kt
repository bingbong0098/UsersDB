package com.example.usersdb.ui.main

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.media.MediaFormat.KEY_LANGUAGE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersdb.R
import com.example.usersdb.databinding.ActivityMainBinding
import com.example.usersdb.db.UserDao
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.addUser.AddUserActivity
import com.example.usersdb.ui.main.adapter.UsersAdapter
import com.example.usersdb.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UsersAdapter.userClickListiner {

    @Inject
    lateinit var usersAdapter: UsersAdapter

    @Inject
    lateinit var userEntity: UserEntity

    @Inject
    lateinit var userDao: UserDao

    val viewModel: MainViewModel by viewModels()

    var deleteList: List<Int?> = mutableListOf()

    lateinit var prefs: SharedPreferences

    lateinit var editor : SharedPreferences.Editor

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        editor = prefs.edit()

//        if (prefs.getBoolean("KEY_THEME", true)){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }

//        Log.e("3636", "" + prefs.getBoolean("KEY_THEME", true))
        binding.apply {

            // تغییر تنظیمات زبان
            val locale = Locale(getCurrentLanguage())
            Locale.setDefault(locale)
            val config = resources.configuration
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)


            switch1?.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    editor.putBoolean("KEY_THEME", true)
                    langBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorButtonLight))
                    addBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorButtonLight))
                    deleteBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorButtonLight))

                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    editor.putBoolean("KEY_THEME", false)
                    langBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSwitch))
                    addBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSwitch))
                    deleteBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSwitch))
                }
            }

            langBtn.setOnClickListener {
                // تغییر زبان
                toggleLanguage()
                // راه‌اندازی مجدد فعالیت (Activity)
                recreate()
            }

            usersRv.apply {

                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = usersAdapter
                usersAdapter.listener = this@MainActivity

            }
            viewModel.allUsers().observe(this@MainActivity) {
                usersAdapter.differ.submitList(it)
            }

            addBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
                finish()
            }

            deleteBtn.setOnClickListener {

                viewModel.deleteItemUser(deleteList)
            }
        }
    }
    override fun users(users: MutableList<Int?>) {
        deleteList = users
    }


    private fun toggleLanguage() {
        val currentLanguage = getCurrentLanguage()
        val newLanguage = if (currentLanguage == "fa") {
            "en"
        } else {
            "fa"
        }
        // ذخیره زبان جدید
        saveCurrentLanguage(newLanguage)

        // تغییر تنظیمات زبان
        val locale = Locale(newLanguage)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun saveCurrentLanguage(language: String) {

        editor.putString("KEY_LANGUAGE", language)
        editor.apply()
    }

    private fun getCurrentLanguage() = prefs.getString("KEY_LANGUAGE", "")
}