package com.example.splitbill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SplitBillActivity : AppCompatActivity(), AdapterListener {

    private lateinit var adapter: UserAdapter
    private lateinit var userListRecyclerView: RecyclerView
    private var amount: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_bill)
        setSupportActionBar(findViewById(R.id.tb_split_bill_toolbar))
        supportActionBar?.title ="Back"

        val paymentAmountTextView : TextView = findViewById(R.id.tv_value_amount)
        amount = intent.getIntExtra("paymentAmount", 0).toLong()
        paymentAmountTextView.text = amount.toString()

        userListRecyclerView = findViewById<RecyclerView>(R.id.rv_user_list_recycler_view)

        adapter = UserAdapter()
        adapter.addAdapterListener(this)
        adapter.addParticipant(User(1, "Current User", amount, true))

        val addButton = findViewById<TextView>(R.id.tv_plus_button)
        addButton.setOnClickListener {
            addParticipant()
        }
        userListRecyclerView.adapter = adapter

        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun addParticipant() {
        adapter.addParticipant(User(adapter.itemCount + 1, "DDDDDDDDDD", 0, false))
        splitAmountEvenly()
    }

    fun removeParticipant(user: User) {
        adapter.removeParticipant(user)
        splitAmountEvenly()
    }

    fun splitAmountEvenly(){
        val amountEachUser = amount / adapter.itemCount
        adapter.updateAmount(amountEachUser)
    }

    data class User(
        val id: Int,
        val name: String,
        var amount: Long,
        val isCurrentUser: Boolean
    )

    class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        private var mListener:AdapterListener? = null
        private val userList = mutableListOf<User>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val currentItem = userList[position]
            val tvUserName = holder.itemView.findViewById<TextView>(R.id.tv_user_name)
            val tvUserAmount = holder.itemView.findViewById<TextView>(R.id.tv_user_amount)
            tvUserName.text = currentItem.name
            tvUserAmount.text = currentItem.amount.toString()

            val tvRemoveButton = holder.itemView.findViewById<TextView>(R.id.tv_remove_button)
            if (currentItem.isCurrentUser){
                tvRemoveButton.visibility = View.GONE
            } else {
                tvRemoveButton.visibility = View.VISIBLE
            }

            tvRemoveButton.setOnClickListener {
                mListener?.onRemoveParticipant(currentItem)
            }

        }

        override fun getItemCount() = userList.size

        fun addAdapterListener(listener: AdapterListener){
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

        fun updateAmount(splitAmount: Long){
            for (user in userList){
                user.amount = splitAmount
            }
        }

    }

    override fun onRemoveParticipant(user: User) {
        removeParticipant(user)
    }
}

interface AdapterListener{
    fun onRemoveParticipant(user: SplitBillActivity.User)
}