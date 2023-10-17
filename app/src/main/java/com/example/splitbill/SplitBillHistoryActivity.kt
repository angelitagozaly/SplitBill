package com.example.splitbill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitbill.databinding.ActivitySplitBillHistoryBinding

class SplitBillHistoryActivity : AppCompatActivity(), SplitBillHistoryAdapterListener{

    private lateinit var mAdapter: SplitBillHistoryAdapter
    private lateinit var binding: ActivitySplitBillHistoryBinding
    private val repository = SplitBillHistoryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitBillHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistoryListToolbar)
        supportActionBar?.title ="Back"

        setupViews()
    }

    private fun setupViews() {
        // setup recyclerview
        mAdapter = SplitBillHistoryAdapter(repository.getSplitBillHistory())
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

    override fun onSplitBillHistorySelected(splitBillHistory: SplitBillHistory) {
        //write function
    }
}