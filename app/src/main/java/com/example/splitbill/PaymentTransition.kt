package com.example.splitbill

import androidx.annotation.DrawableRes

class PaymentTransition(
    val id: Int,
    val slipNumber: String,
    val amount: Long,
    val pointEarned: Long,
    val createdAt: Long,
    val merchant: Merchant
)

class Merchant(
    val id: Int,
    val name: String,
    @DrawableRes
    val imageResId: Int,
)