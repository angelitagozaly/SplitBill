package com.example.splitbill

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplitBillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_bill)
        setSupportActionBar(findViewById(R.id.split_bill_toolbar))
        supportActionBar?.title ="Back"

        val paymentAmountTextView : TextView = findViewById(R.id.value_amount)
        val amount = intent.getIntExtra("paymentAmount", 0).toString()
        paymentAmountTextView.text = amount
    }
}