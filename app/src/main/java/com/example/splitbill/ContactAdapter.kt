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
    inner class ContactViewHolder(val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val contactList = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.binding.tvContactName.text = currentItem.name
        holder.binding.tvContactNumber.text = currentItem.number
    }

    override fun getItemCount() = contactList.size

    fun createContactList(){
        contactList.add(Contact(1, "AAAAA", "123456789"))
        contactList.add(Contact(2, "BBBBB", "123456789"))
        contactList.add(Contact(3, "CCCCC", "123456789"))
        contactList.add(Contact(4, "DDDDD", "123456789"))
        contactList.add(Contact(5, "EEEEE", "123456789"))
    }
}