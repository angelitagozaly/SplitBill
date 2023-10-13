package com.example.splitbill

val merchants = listOf(
    Merchant(
        id = 1,
        name = "Rakuten Cafe",
        imageResId = R.drawable.ic_rakuten
    ),
    Merchant(
        id = 2,
        name = "7 Eleven",
        imageResId = R.drawable.ic_7eleven
    ),
    Merchant(
        id = 3,
        name = "Super",
        imageResId = R.drawable.ic_super
    ),
    Merchant(
        id = 4,
        name = "MITSUI Outlet Park",
        imageResId = R.drawable.ic_super
    )
)

val transitions = listOf(
    PaymentTransition(
        id = 1,
        slipNumber = "ea6e364d-68ab-4bbe-beeb-99984c76b856",
        amount = 2200,
        pointEarned = 22,
        createdAt = 1694064071,
        merchant = merchants[0]
    ),
    PaymentTransition(
        id = 2,
        slipNumber = "719c7ba8-124a-4f1d-8f34-5e03fce1039c",
        amount = 2400,
        pointEarned = 24,
        createdAt = 1694064071,
        merchant = merchants[0]
    ),
    PaymentTransition(
        id = 3,
        slipNumber = "be5538c6-7172-4c59-b289-49ad96c13551",
        amount = 1240,
        pointEarned = 12,
        createdAt = 1694064071,
        merchant = merchants[2]
    ),
    PaymentTransition(
        id = 4,
        slipNumber = "1d31a24b-f296-4369-adc9-6ec3c5abda15",
        amount = 4398,
        pointEarned = 43,
        createdAt = 1694064071,
        merchant = merchants[3]
    ),
    PaymentTransition(
        id = 5,
        slipNumber = "1588b2c4-cead-4190-88c1-0212f42e2fd2",
        amount = 746,
        pointEarned = 7,
        createdAt = 1694064071,
        merchant = merchants[1]
    )
)