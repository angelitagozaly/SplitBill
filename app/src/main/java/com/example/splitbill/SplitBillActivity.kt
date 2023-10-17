package com.example.splitbill

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitbill.databinding.ActivitySplitBillBinding

const val PAYAMT = "paymentAmount"
const val PAYDESC = "paymentDescription"
const val PAYDATE = "paymentDate"
const val SELECTCONT = "SELECTED_CONTACT"

class SplitBillActivity : AppCompatActivity(), UserAdapterListener, DialogListener {

    private lateinit var adapter: UserAdapter
    private var amount: Long = 0
    private lateinit var binding: ActivitySplitBillBinding
    private lateinit var dialog: ConfirmationDialogFragment

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
        setPaymentDescriptionTextView()
        setPaymentDateTextView()
        setRecyclerView()
        setAddButtonListener()
        setConfirmationButtonListener()
        setDialogListener()
    }

    private fun setPaymentAmountTextView() {
        val paymentAmountTextView : TextView = binding.tvValueAmount
        amount = intent.getLongExtra(PAYAMT, 0)
        paymentAmountTextView.text = amount.toAmountFormat()
    }

    private fun setPaymentDescriptionTextView(){
        val paymentDescriptionTextView : TextView = binding.tvPaymentDescription
        paymentDescriptionTextView.text = intent.getStringExtra(PAYDESC)
    }

    private fun setPaymentDateTextView(){
        val paymentDateTextView : TextView = binding.tvPaymentDate
        paymentDateTextView.text = intent.getStringExtra(PAYDATE)
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
        val numOfParticipant = adapter.itemCount
        if (numOfParticipant == 0) return
        var amountEachUser = amount / numOfParticipant
        val remainderEachUser = (amount % numOfParticipant) % numOfParticipant
        if (remainderEachUser > 0.5){
            amountEachUser++
        }
        adapter.updateAmount(amountEachUser)
        setConfirmationButtonListener()
    }

    private fun addParticipant(id: String, name: String){
        adapter.addParticipant(User(id.toInt(), name, amount, false))
        splitAmountEvenly()
    }

    override fun onRemoveParticipant(user: User) {
        adapter.removeParticipant(user)
        splitAmountEvenly()
    }

    private fun setConfirmationButtonListener(){
        val confirmButton = binding.btConfirm
        confirmButton.setOnClickListener{
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog(){
        val userList = adapter.getUserList()
        val bundle = Bundle()
        val sb = StringBuilder()
        val sb2 = StringBuilder()
        for (user in userList) {
            sb.append(user.name)
            sb2.append(user.amount.toString())
            sb.append('\n')
            sb2.append('\n')
        }
        val resultName = sb.toString()
        val resultAmount = sb2.toString()
        bundle.putString("names", resultName)
        bundle.putString("amounts", resultAmount)

        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "CONFIRMATION_DIALOG")
    }

    override fun showNotificationConfirmationDialog(){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Notification has been sent!")
            .setPositiveButton("Ok") { dialog, id ->
                dialog.dismiss()
                val intent = Intent(this, SplitBillHistoryActivity::class.java)
                startActivity(intent)
            }
        dialog.create()
        dialog.show()
    }

    private fun setDialogListener(){
        dialog = ConfirmationDialogFragment()
        dialog.addDialogListener(this)
    }
}

interface UserAdapterListener{
    fun onRemoveParticipant(user: User)
}

interface DialogListener{
    fun showNotificationConfirmationDialog()
}