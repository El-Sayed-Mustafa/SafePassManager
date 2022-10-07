package com.example.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.room.db.AccountRepo

class AccountViewModelFactory(private val repo: AccountRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java))
            return AccountViewModel(repo) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}