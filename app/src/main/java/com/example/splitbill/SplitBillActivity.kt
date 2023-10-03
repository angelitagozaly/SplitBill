package com.example.splitbill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SplitBillActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var userListRecyclerView: RecyclerView
    private var amount: Long = 0

    val userList = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_bill)
        setSupportActionBar(findViewById(R.id.tb_split_bill_toolbar))
        supportActionBar?.title ="Back"

        val paymentAmountTextView : TextView = findViewById(R.id.tv_value_amount)
        amount = intent.getIntExtra("paymentAmount", 0).toLong()
        paymentAmountTextView.text = amount.toString()

        userList.add(User(1, "Current User", amount, true))

        userListRecyclerView = findViewById<RecyclerView>(R.id.rv_user_list_recycler_view)

        adapter = UserAdapter(userList)

        val addButton = findViewById<TextView>(R.id.tv_plus_button)
        addButton.setOnClickListener {
            addParticipant()
        }

        userListRecyclerView.adapter = adapter

        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun addParticipant() {
        userList.add(User(userList.size + 1, "DDDDDDDDDD", 0, false))
        splitAmountEvenly()
        adapter = UserAdapter(userList)
        userListRecyclerView.adapter = adapter
    }

    fun splitAmountEvenly(){
        val amountEachUser = amount / userList.size
        for (user in userList) {
            user.amount = amountEachUser
        }
    }
    data class User(
        val id: Int,
        val name: String,
        var amount: Long,
        val isCurrentUser: Boolean
    )

    class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val currentItem = userList[position]
            holder.itemView.findViewById<TextView>(R.id.tv_user_name).text = currentItem.name
            holder.itemView.findViewById<TextView>(R.id.tv_user_amount).text = currentItem.amount.toString()
        }

        override fun getItemCount() = userList.size

    }
}