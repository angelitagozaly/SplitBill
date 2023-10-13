package com.example.splitbill

import android.content.Intent
import android.os.Bundle
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

}
