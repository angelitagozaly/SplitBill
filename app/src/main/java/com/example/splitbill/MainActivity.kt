package com.example.splitbill

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : ComponentActivity() {

    private lateinit var mAdapter: PaymentTransitionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_history)
        setupViews()
        setSplitHistoryButtonListener()
    }

    private fun setupViews() {
        // setup recyclerview
        mAdapter = PaymentTransitionAdapter(transitions)
        mAdapter.setAdapterListener { id ->
            openTransitionDetail(id)
        }

        findViewById<RecyclerView>(R.id.rv_transitions).apply {
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

    private fun openTransitionDetail(id: Int) {
        val intent = Intent(this, PaymentTransitionDetailActivity::class.java)
        intent.putExtra(KEY_PAYMENT_TRANSITION_ID, id)
        startActivity(intent)
    }

    private fun setSplitHistoryButtonListener() {
        val btSplitBill = findViewById<Button>(R.id.bt_split_history)
        btSplitBill.setOnClickListener{
            val intent = Intent(this, SplitBillHistoryActivity::class.java)
            startActivity(intent)
        }
    }

}
