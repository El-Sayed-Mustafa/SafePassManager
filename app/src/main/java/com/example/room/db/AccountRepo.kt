package com.example.room.db

class AccountRepo(private val Dao: AccountDao)
{
    val accounts = Dao.getAll()

    suspend fun deleteAll(account: Account){
        Dao.DeleteAll()
    }
    suspend fun insert(account:Account){
        Dao.InsertAccount(account)
    }

    suspend fun update(account: Account){
        Dao.UpdateAccount(account)
    }


    suspend fun delete(account: Account){
        Dao.DeleteAccount(account)
    }

}