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
import com.example.splitbill.databinding.ActivitySplitBillBinding

class SplitBillActivity : AppCompatActivity(), AdapterListener {

    private lateinit var adapter: UserAdapter
    private lateinit var userListRecyclerView: RecyclerView
    private var amount: Long = 0
    private lateinit var binding: ActivitySplitBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbSplitBillToolbar)
        supportActionBar?.title ="Back"

        setPaymentAmountTextView()
        setRecyclerView()
        setAddButtonListener()
    }

    fun setPaymentAmountTextView() {
        val paymentAmountTextView : TextView = binding.tvValueAmount
        amount = intent.getIntExtra("paymentAmount", 0).toLong()
        paymentAmountTextView.text = amount.toString()
    }

    fun setRecyclerView() {
        userListRecyclerView = binding.rvUserListRecyclerView
        adapter = UserAdapter()
        adapter.addAdapterListener(this)
        adapter.addParticipant(User(1, "Current User", amount, true))
        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun setAddButtonListener(){
        val addButton = binding.tvPlusButton
        addButton.setOnClickListener {
            addParticipant()
        }
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

    override fun onRemoveParticipant(user: User) {
        removeParticipant(user)
    }
}

interface AdapterListener{
    fun onRemoveParticipant(user: SplitBillActivity.User)
}