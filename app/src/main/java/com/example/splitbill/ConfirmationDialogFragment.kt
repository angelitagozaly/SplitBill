package com.example.splitbill

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.splitbill.databinding.ActivitySplitBillBinding
import com.example.splitbill.databinding.ConfirmationDialogBinding

class ConfirmationDialogFragment : DialogFragment() {

    private lateinit var binding: ConfirmationDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = ConfirmationDialogBinding.inflate(layoutInflater)
            builder.setView(binding.root)
            setXButtonListener()
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setXButtonListener(){
        val xButton = binding.tvCloseConfirmationDialog
        xButton.setOnClickListener {
            dialog?.cancel()
        }
    }
}