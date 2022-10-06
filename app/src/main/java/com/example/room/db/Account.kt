package com.example.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val siteName:String,
    val email:String,
    val password:String
)
