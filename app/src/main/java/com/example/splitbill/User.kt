package com.example.splitbill

data class User(
    val id: Int,
    val name: String,
    var amount: Long,
    val isCurrentUser: Boolean
)