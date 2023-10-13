package com.example.splitbill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class PaymentTransitionAdapter(items: List<PaymentTransition>) :
    RecyclerView.Adapter<PaymentTransitionAdapter.ItemViewHolder>() {
    private val mItems: List<PaymentTransition>
    private var mClickListener: PaymentTransitionAdapterListener? = null

    class ItemViewHolder(view: View) : ViewHolder(view) {
        var ivMerchantIcon: ImageView
        var tvMerchantName: TextView
        var tvCreatedAt: TextView
        var tvAmount: TextView

        init {
            ivMerchantIcon = view.findViewById<View>(R.id.iv_merchant_icon) as ImageView
            tvMerchantName = view.findViewById<View>(R.id.tv_merchant_name) as TextView
            tvCreatedAt = view.findViewById<View>(R.id.tv_created_at) as TextView
            tvAmount= view.findViewById<View>(R.id.tv_amount) as TextView
        }
    }

    init {
        mItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transition_history, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: PaymentTransition = mItems[position]
        holder.tvMerchantName.text = item.merchant.name
        holder.ivMerchantIcon.setImageResource(item.merchant.imageResId)
        holder.tvAmount.text = item.amount.toAmountFormat()
        holder.tvCreatedAt.text = item.createdAt.toTimeFormat1()
        holder.itemView.setOnClickListener {
            this.mClickListener?.onPaymentTransitionItemClicked(item.id)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setAdapterListener(listener: PaymentTransitionAdapterListener) {
        this.mClickListener = listener
    }

}

fun interface PaymentTransitionAdapterListener {
    fun onPaymentTransitionItemClicked(id: Int)
}