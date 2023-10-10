package com.example.splitbill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.ContactListItemBinding
import com.example.splitbill.databinding.UserListItemBinding

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var binding: ContactListItemBinding
    private var contactList = mutableListOf<Contact>()

    inner class ContactViewHolder(val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.binding.tvContactName.text = currentItem.name
        holder.binding.tvContactNumber.text = currentItem.number
    }

    fun setContactList(contacts: List<Contact>){
        contactList = contacts.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = contactList.size
}