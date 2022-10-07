package com.example.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.db.Account
import com.example.room.db.AccountRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val repo: AccountRepo) : ViewModel() {

    val accounts = repo.accounts


    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputPass = MutableLiveData<String>()

    val upsertButton = MutableLiveData<String>()
    val clearButton = MutableLiveData<String>()

    init {
        upsertButton.value = "Save"
        clearButton.value = "Clear All"
    }


    fun Upsert() {

        val name = inputName.value!!
        val email = inputEmail.value!!
        val pass = inputPass.value!!

        insert(Account(0, name, email, pass))

        inputEmail.value = ""
        inputName.value = ""
        inputPass.value= ""
    }

    fun Clear() {
        clearAll()
    }


    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(account)
    }


    fun update(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(account)
    }

    fun delete(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(account)
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}