package com.example.room.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAccount(account: Account):Long

    @Update
    suspend fun UpdateAccount(account: Account)

    @Delete
    suspend fun DeleteAccount(account: Account)

    @Query("DELETE FROM account_table")
    suspend fun DeleteAll()

    @Query("SELECT * FROM account_table")
    fun getAll():LiveData<List<Account>>
}