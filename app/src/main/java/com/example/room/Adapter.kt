package com.example.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ListItemBinding
import com.example.room.db.Account
import com.example.room.generated.callback.OnClickListener

class Adapter(
    private val accountsList: List<Account>,
    private val clickListener: (Account) -> Unit,
) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(accountsList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return accountsList.size
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(account: Account, clickListener: (Account) -> Unit) {
        binding.tvName.text = account.siteName
        binding.tvEmail.text = account.email
        binding.tvPass.text = account.password
        binding.listItem.setOnClickListener{
            clickListener(account)
        }
    }
}