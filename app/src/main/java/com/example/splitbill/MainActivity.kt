package com.example.splitbill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btSplitBill = findViewById<Button>(R.id.bt_split_bill)
        btSplitBill.setOnClickListener{
            val intent = Intent(this, SplitBill::class.java)
            //intent.putExtra()
            startActivity(intent)
        }
    }
}