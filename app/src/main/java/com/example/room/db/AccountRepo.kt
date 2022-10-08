package com.example.room.db

class AccountRepo(private val Dao: AccountDao) {
    val accounts = Dao.getAll()

    suspend fun deleteAll(): Int {
        return Dao.DeleteAll()
    }

    suspend fun insert(account: Account): Long {
        return Dao.InsertAccount(account)
    }

    suspend fun update(account: Account): Int {
        return Dao.UpdateAccount(account)
    }


    suspend fun delete(account: Account): Int {
        return Dao.DeleteAccount(account)
    }

}