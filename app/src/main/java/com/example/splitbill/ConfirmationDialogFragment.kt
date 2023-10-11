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
    private var dListener: DialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val names = arguments?.getString("names")
            val amounts = arguments?.getString("amounts")
            binding = ConfirmationDialogBinding.inflate(layoutInflater)
            builder.setView(binding.root)
            setTextView(names!!, amounts!!)
            setXButtonListener()
            setConfirmButtonListener()
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setTextView(names: String, amounts: String){
        binding.tvParticipantNameList.text = names
        binding.tvParticipantAmountList.text = amounts
    }

    private fun setXButtonListener(){
        val xButton = binding.tvCloseConfirmationDialog
        xButton.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun setConfirmButtonListener(){
        val confirmButton = binding.btSendNotification
        confirmButton.setOnClickListener {
            dialog?.dismiss()
            informDialogListener()
        }
    }

    fun addDialogListener(listener: DialogListener){
        this.dListener = listener
    }

    private fun informDialogListener(){
        dListener?.showNotificationConfirmationDialog()
    }
}