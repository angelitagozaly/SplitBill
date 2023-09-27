package com.example.splitbill

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class SplitBill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_bill)
        //intent.getStringExtra()
        setSupportActionBar(findViewById(R.id.split_bill_toolbar))
        getSupportActionBar()?.setTitle("Back")
    }
}