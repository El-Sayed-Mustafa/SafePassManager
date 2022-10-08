package com.example.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.db.Account
import com.example.room.db.AccountRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(private val repo: AccountRepo) : ViewModel() {

    val accounts = repo.accounts
    private var isUpdateAndDelete = false
    private lateinit var accountToUpdateOrDelete: Account

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputPass = MutableLiveData<String>()

    val upsertButton = MutableLiveData<String>()
    val clearButton = MutableLiveData<String>()

    init {
        inputEmail.value = ""
        inputPass.value = ""
        inputName.value = ""
        upsertButton.value = "Save"
        clearButton.value = "Clear All"
    }


    fun Upsert() {

        if (isUpdateAndDelete) {
            accountToUpdateOrDelete.siteName = inputName.value!!
            accountToUpdateOrDelete.email = inputEmail.value!!
            accountToUpdateOrDelete.password = inputPass.value!!
            update(accountToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            val pass = inputPass.value!!

            insert(Account(0, name, email, pass))

            inputEmail.value = ""
            inputName.value = ""
            inputPass.value = ""
        }
    }

    fun Clear() {
        if (isUpdateAndDelete)
            delete(accountToUpdateOrDelete)
        else
            clearAll()
    }


    fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(account)
    }


    fun update(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(account)

        withContext(Dispatchers.Main) {
            inputName.value = ""
            inputEmail.value = ""
            inputPass.value = ""

            isUpdateAndDelete = false

            upsertButton.value = "Save"
            clearButton.value = "Clear All"

        }
    }

    fun delete(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(account)

        withContext(Dispatchers.Main) {
            inputName.value = ""
            inputEmail.value = ""
            inputPass.value = ""

            isUpdateAndDelete = false

            upsertButton.value = "Save"
            clearButton.value = "Clear All"

        }
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    fun initUpdateAndDelete(account: Account) {

        inputName.value = account.siteName
        inputEmail.value = account.email
        inputPass.value = account.password

        isUpdateAndDelete = true

        accountToUpdateOrDelete = account

        upsertButton.value = "Update"
        clearButton.value = "Delete"

    }


}