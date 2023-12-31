package com.example.splitbill

data class SplitBillHistory(
    val id: Int,
    val date: String,
    val merchantName: String,
    val amount: Long,
    val participantList: List<SplitParticipant>,
    var paidCount: Int
)