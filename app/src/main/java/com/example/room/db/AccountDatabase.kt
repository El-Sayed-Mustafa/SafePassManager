package com.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {

    abstract val AccountDao:AccountDao

    companion object{
        @Volatile
        private var INSTANCE:AccountDatabase?=null

        fun getInstance(context: Context):AccountDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AccountDatabase::class.java,
                        "account_table"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}