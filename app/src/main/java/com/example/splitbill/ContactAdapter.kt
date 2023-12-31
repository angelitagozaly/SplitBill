package com.example.splitbill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.ItemContactListBinding

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var binding: ItemContactListBinding
    private var mListener: ContactAdapterListener? = null
    private var contactList = mutableListOf<Contact>()

    inner class ContactViewHolder(val binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.binding.tvContactName.text = currentItem.name
        holder.binding.tvContactNumber.text = currentItem.number
        holder.binding.llContactList.setOnClickListener {
            mListener?.onContactSelected(currentItem)
        }
    }

    fun setContactList(contacts: List<Contact>){
        contactList = contacts.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = contactList.size

    fun addAdapterListener(listener: ContactAdapterListener) {
        this.mListener = listener
    }
}