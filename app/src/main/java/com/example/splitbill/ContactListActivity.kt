package com.example.splitbill

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitbill.databinding.ActivityContactListBinding


class ContactListActivity : AppCompatActivity(){

    private lateinit var adapter: ContactAdapter
    private lateinit var binding: ActivityContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbContactListToolbar)
        supportActionBar?.title ="Back"

        setUpRecyclerView()
        fetchContactList()
    }

    private fun setUpRecyclerView() {
        val userListRecyclerView = binding.rvContactListRecyclerView
        adapter = ContactAdapter()
        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchContactList(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.READ_CONTACTS), 100);
        } else {
            adapter.setContactList(getContactList())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapter.setContactList(getContactList())
        }
    }

    private fun getContactList(): List<Contact> {
        val contactList = mutableListOf<Contact>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID)).toInt()
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = Contact(id, name, phone)
                contactList.add(contact)
            }
            it.close()
        }

        return contactList
    }
}