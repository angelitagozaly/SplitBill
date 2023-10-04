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

const val PAYAMT = "paymentAmount"
class SplitBillActivity : AppCompatActivity(), AdapterListener {

    private lateinit var adapter: UserAdapter
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

    private fun setPaymentAmountTextView() {
        val paymentAmountTextView : TextView = binding.tvValueAmount
        amount = intent.getIntExtra(PAYAMT, 0).toLong()
        paymentAmountTextView.text = amount.toString()
    }

    private fun setRecyclerView() {
        val userListRecyclerView = binding.rvUserListRecyclerView
        adapter = UserAdapter()
        adapter.addAdapterListener(this)
        adapter.addParticipant(User(1, "Current User", amount, true))
        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setAddButtonListener(){
        val addButton = binding.tvPlusButton
        addButton.setOnClickListener {
            addParticipant()
        }
    }

    private fun addParticipant() {
        adapter.addParticipant(User(adapter.itemCount + 1, "DDDDDDDDDD", 0, false))
        splitAmountEvenly()
    }

    private fun removeParticipant(user: User) {
        adapter.removeParticipant(user)
        splitAmountEvenly()
    }

    private fun splitAmountEvenly(){
        var amountEachUser = amount / adapter.itemCount
        val remainderEachUser = (amount % adapter.itemCount) % adapter.itemCount
        if (remainderEachUser > 0.5){
            amountEachUser++
        }
        adapter.updateAmount(amountEachUser)
    }

    override fun onRemoveParticipant(user: User) {
        removeParticipant(user)
    }
}

interface AdapterListener{
    fun onRemoveParticipant(user: User)
}