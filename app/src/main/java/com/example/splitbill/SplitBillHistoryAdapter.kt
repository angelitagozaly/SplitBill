package com.example.splitbill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.splitbill.databinding.ItemSplitBillHistoryBinding

class SplitBillHistoryAdapter(histories: List<SplitBillHistory>) : RecyclerView.Adapter<SplitBillHistoryAdapter.SplitBillHistoryViewHolder>() {

    private lateinit var binding: ItemSplitBillHistoryBinding
    private var mListener: SplitBillHistoryAdapterListener? = null
    private val mHistories : List<SplitBillHistory>

    init{
        mHistories = histories
    }

    inner class SplitBillHistoryViewHolder(val binding: ItemSplitBillHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitBillHistoryViewHolder {
        binding = ItemSplitBillHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SplitBillHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SplitBillHistoryViewHolder, position: Int) {
        val currentItem = mHistories[position]
        holder.binding.tvSplitHistoryMerchant.text = currentItem.merchantName
        holder.binding.tvSplitHistoryDate.text = currentItem.date
        holder.binding.tvSplitHistoryAmount.text = currentItem.amount.toAmountFormat()
        holder.binding.tvSplitHistoryPaidCount.text = currentItem.paidCount.toString()
        holder.binding.tvSplitHistoryPaidCountMax.text = currentItem.participantList.size.toString()
        holder.itemView.setOnClickListener {
            this.mListener?.onSplitBillHistorySelected(currentItem)
        }
    }

    override fun getItemCount() = mHistories.size

    fun setAdapterListener(listener: SplitBillHistoryAdapterListener) {
        this.mListener = listener
    }
}

interface SplitBillHistoryAdapterListener{
    fun onSplitBillHistorySelected(splitBillHistory: SplitBillHistory)
}