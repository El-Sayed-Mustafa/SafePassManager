package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.Account
import com.example.room.db.AccountDatabase
import com.example.room.db.AccountRepo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = AccountDatabase.getInstance(application).AccountDao
        val repo = AccountRepo(dao)
        val factory = AccountViewModelFactory(repo)

        accountViewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]
        binding.myViewModel = accountViewModel

        binding.lifecycleOwner = this
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.accountRecyclerView.layoutManager = LinearLayoutManager(this)
        displayAccountsList()

    }

    private fun displayAccountsList() {
        accountViewModel.accounts.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            binding.accountRecyclerView.adapter = Adapter(it) { selectedItem: Account ->
                listItemClicked(selectedItem)
            }
        })
    }

    private fun listItemClicked(account: Account) {
        Toast.makeText(this, "Selected name is ${account.siteName}", Toast.LENGTH_LONG).show()
        accountViewModel.initUpdateAndDelete(account)
    }

}