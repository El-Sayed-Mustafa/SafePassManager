package com.example.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var siteName:String,
    var email:String,
    var password:String
)
