package com.example.splitbill

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.ActivitySplitBillBinding
import com.example.splitbill.databinding.ContactListItemBinding

const val PAYAMT = "paymentAmount"
const val SELECTCONT = "SELECTED_CONTACT"

class SplitBillActivity : AppCompatActivity(), UserAdapterListener {

    private lateinit var adapter: UserAdapter
    private var amount: Long = 0
    private lateinit var binding: ActivitySplitBillBinding

    private val contactSelectionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedContact: Contact? = data?.getParcelableExtra(SELECTCONT)

            if (selectedContact != null) {
                addParticipant(selectedContact.id.toString(), selectedContact.name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbSplitBillToolbar)
        supportActionBar?.title ="Back"

        setPaymentAmountTextView()
        setRecyclerView()
        setAddButtonListener()
        setConfirmationButtonListener()
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
        adapter.addParticipant(User(0, "Current User", amount, true))
        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setAddButtonListener(){
        val addButton = binding.tvPlusButton
        addButton.setOnClickListener {
            val intent = Intent(this, ContactListActivity::class.java)
            contactSelectionLauncher.launch(intent)
        }
    }

    private fun splitAmountEvenly(){
        var numOfParticipant = adapter.itemCount
        if (numOfParticipant == 0) return
        var amountEachUser = amount / numOfParticipant
        val remainderEachUser = (amount % numOfParticipant) % numOfParticipant
        if (remainderEachUser > 0.5){
            amountEachUser++
        }
        adapter.updateAmount(amountEachUser)
    }

    override fun onRemoveParticipant(user: User) {
        adapter.removeParticipant(user)
        splitAmountEvenly()
    }

    private fun setConfirmationButtonListener(){
        val confirmButton = binding.btConfirm
        confirmButton.setOnClickListener{
            ConfirmationDialogFragment().show(supportFragmentManager, "CONFIRMATION_DIALOG")
        }
    }

    private fun addParticipant(id: String, name: String){
        adapter.addParticipant(User(id.toInt(), name, amount, false))
        splitAmountEvenly()
    }
}

interface UserAdapterListener{
    fun onRemoveParticipant(user: User)
}