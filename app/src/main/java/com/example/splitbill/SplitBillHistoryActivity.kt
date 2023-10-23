package com.example.splitbill

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitbill.databinding.ActivitySplitBillHistoryBinding

class SplitBillHistoryActivity : AppCompatActivity(), SplitBillHistoryAdapterListener{

    private lateinit var mAdapter: SplitBillHistoryAdapter
    private lateinit var binding: ActivitySplitBillHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitBillHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistoryListToolbar)
        supportActionBar?.title ="Back"

        addToHistory()
        setupViews()
    }

    private fun setupViews() {
        // setup recyclerview
        mAdapter = SplitBillHistoryAdapter(SplitBillHistoryRepository.getSplitBillHistory())
        mAdapter.setAdapterListener(this)
        binding.rvHistoryListRecyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(this.context)
            adapter = mAdapter
        }
    }

    private fun addToHistory(){
        val date = intent.getStringExtra(PAYDATE)
        val merchant = intent.getStringExtra(PAYDESC)
        val amount = intent.getLongExtra(PAYAMT, 0)
        val participantList: ArrayList<SplitParticipant> = intent.getParcelableArrayListExtra(PARTICIPANTS)!!
        SplitBillHistoryRepository.addSplitBillHistory(SplitBillHistory(SplitBillHistoryRepository.getSize()+1, date!!, merchant!!, amount, participantList, 0))
    }

    override fun onSplitBillHistorySelected(splitBillHistory: SplitBillHistory) {
        val participantNames = splitBillHistory.participantList.map { it.name }.toTypedArray()
        val checkedItems = splitBillHistory.participantList.map { it.paidStatus }.toBooleanArray()
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("List of Participants")
        dialog.setMultiChoiceItems(participantNames, checkedItems) { _, position, isChecked ->
            SplitBillHistoryRepository.updatePaidStatus(splitBillHistory.id - 1, position, isChecked)
        }
        dialog.setPositiveButton("Update Status") { dialog, id ->
            dialog.cancel()
            SplitBillHistoryRepository.updatePaidCount(splitBillHistory.id - 1)
            mAdapter.notifyDataSetChanged()
        }
        dialog.create()
        dialog.show()
    }
}