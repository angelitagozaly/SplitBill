package com.example.splitbill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.ActivityContactListBinding
import com.example.splitbill.databinding.ActivitySplitBillBinding

class ContactListActivity : AppCompatActivity(){

    private lateinit var adapter: ContactAdapter
    private lateinit var binding: ActivityContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbContactListToolbar)
        supportActionBar?.title ="Back"

        setRecyclerView()
    }
    private fun setRecyclerView() {
        val userListRecyclerView = binding.rvContactListRecyclerView
        adapter = ContactAdapter()
        adapter.createContactList()
        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}