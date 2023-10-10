package com.example.splitbill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.UserListItemBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var binding: UserListItemBinding
    private var mListener: UserAdapterListener? = null
    private val userList = mutableListOf<User>()

    inner class UserViewHolder(val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.binding.tvUserName.text = currentItem.name
        holder.binding.tvUserAmount.text = currentItem.amount.toString()

        if (currentItem.isCurrentUser) {
            holder.binding.tvRemoveButton.visibility = View.GONE
        } else {
            holder.binding.tvRemoveButton.visibility = View.VISIBLE
        }

        holder.binding.tvRemoveButton.setOnClickListener {
            mListener?.onRemoveParticipant(currentItem)
        }

    }

    override fun getItemCount() = userList.size

    fun getUserList() = userList

    fun addAdapterListener(listener: UserAdapterListener) {
        this.mListener = listener
    }

    fun addParticipant(user: User) {
        userList.add(user)
        notifyDataSetChanged()
    }

    fun removeParticipant(user: User) {
        userList.removeIf {
            it.id == user.id
        }
        notifyDataSetChanged()
    }

    fun updateAmount(splitAmount: Long) {
        for (user in userList) {
            user.amount = splitAmount
        }
    }
}