package com.example.splitbill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splitbill.databinding.ActivityMainBinding

class MainActivityOld : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btSplitBill = binding.btSplitBill
        btSplitBill.setOnClickListener{
            val intent = Intent(this, SplitBillActivity::class.java)
            intent.putExtra("paymentAmount", 45000)
            startActivity(intent)
        }
    }
}