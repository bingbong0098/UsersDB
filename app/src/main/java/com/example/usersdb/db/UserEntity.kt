package com.example.usersdb.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.usersdb.utils.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    var name: String? = "",

    var family: String? = "",

    var age: String? = "",

    var nationalNumber: String? = "",

    var date : String? = ""
)
