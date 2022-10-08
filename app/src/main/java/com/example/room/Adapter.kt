package com.example.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ListItemBinding
import com.example.room.db.Account

class Adapter (private val accountsList: List<Account>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding :ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(accountsList[position])
    }

    override fun getItemCount(): Int {
        return accountsList.size
    }
}

class MyViewHolder(val binding:ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind (account: Account){
        binding.tvName.text = account.siteName
        binding.tvEmail.text = account.email
        binding.tvPass.text = account.password
    }
}