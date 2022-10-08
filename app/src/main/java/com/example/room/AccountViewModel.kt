package com.example.room

import androidx.lifecycle.LiveData
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

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>>
        get() = _statusMessage

    init {
        upsertButton.value = "Save"
        clearButton.value = "Clear All"
    }


    fun Upsert() {

        if (inputName.value==null)
            _statusMessage.value = Event("Please Enter Website name")

        else if (inputEmail.value==null)
            _statusMessage.value = Event("Please Enter your Email")

        else if (inputPass.value == null)
            _statusMessage.value = Event("Please Enter your password")

        else{
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

    }

    fun Clear() {
        if (isUpdateAndDelete)
            delete(accountToUpdateOrDelete)
        else
            clearAll()
    }


    private fun insert(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val newRowId = repo.insert(account)

        withContext(Dispatchers.Main) {
            if (newRowId >= -1)
                _statusMessage.value = Event("Account inserted successfully! $newRowId")
            else
                _statusMessage.value = Event("Error Occurred!")

        }
    }


    private fun update(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repo.update(account)

        withContext(Dispatchers.Main) {

            if (numberOfRows > 0) {
                inputName.value = ""
                inputEmail.value = ""
                inputPass.value = ""

                isUpdateAndDelete = false

                upsertButton.value = "Save"
                clearButton.value = "Clear All"

                _statusMessage.value = Event("$numberOfRows Rows updated successfully!")
            } else {
                _statusMessage.value = Event("Error Occurred")
            }
        }
    }

    private fun delete(account: Account) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repo.delete(account)

        withContext(Dispatchers.Main) {
            if (numberOfRows > 0) {
                inputName.value = ""
                inputEmail.value = ""
                inputPass.value = ""

                isUpdateAndDelete = false

                upsertButton.value = "Save"
                clearButton.value = "Clear All"

                _statusMessage.value = Event("$numberOfRows Rows deleted successfully!")
            } else
                _statusMessage.value = Event("Error Occurred")


        }
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repo.deleteAll()

        withContext(Dispatchers.Main) {
            if (numberOfRows > 0) {
                _statusMessage.value = Event("$numberOfRows Rows deleted successfully!")
            }else{
                _statusMessage.value = Event("Error Occurred")
            }
        }
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