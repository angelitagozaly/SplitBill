package com.example.splitbill

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.splitbill.databinding.ActivityPaymentTransitionDetailBinding

const val KEY_PAYMENT_TRANSITION_ID = "PAYMENT_TRANSITION_ID"
class PaymentTransitionDetailActivity : ComponentActivity() {

    private var _viewBinding: ActivityPaymentTransitionDetailBinding? = null
    private val viewBinding: ActivityPaymentTransitionDetailBinding
        get() = _viewBinding!!
    private lateinit var mItem: PaymentTransition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityPaymentTransitionDetailBinding.inflate(layoutInflater)
            .also { _viewBinding = it }
        setContentView(viewBinding.root)

        val itemId = intent.getIntExtra(KEY_PAYMENT_TRANSITION_ID, -1)
        if (itemId == -1) finish() // Need ID, throw error

        val result = transitions.firstOrNull { it.id == itemId }
        if (result != null) mItem = result else finish() // Cannot find Transition, throw error

        showItem(mItem)
        setSplitBillButtonListener(mItem)

    }

    private fun setSplitBillButtonListener(item: PaymentTransition) {
        val btSplitBill = viewBinding.btSplitBill
        btSplitBill.setOnClickListener{
            val intent = Intent(this, SplitBillActivity::class.java)
            intent.putExtra(PAYAMT, item.amount)
            intent.putExtra(PAYDESC, item.merchant.name)
            intent.putExtra(PAYDATE, item.createdAt.toTimeFormat1())
            startActivity(intent)
        }
    }

    private fun showItem(item: PaymentTransition) = viewBinding.apply {
        // merchant
        this.ivMerchantIcon.setImageResource(item.merchant.imageResId)
        this.tvMerchantName.text = item.merchant.name

        // transition info
        this.tvDateTime.text = item.createdAt.toTimeFormat2()
        this.tvSlipNumber.text = item.slipNumber

        // payment info
        this.tvTotalAmount.text = item.amount.toAmountFormat()
        this.tvPaymentAmount.text = item.amount.toAmountFormat()

        // point info
        this.tvPoint.text = item.pointEarned.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}