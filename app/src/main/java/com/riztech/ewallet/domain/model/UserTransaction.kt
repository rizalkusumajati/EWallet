package com.riztech.ewallet.domain.model

import com.riztech.ewallet.data.local.db.TransactionEntity
import com.riztech.ewallet.data.local.db.UserEntity


data class UserTransaction(
    val sender: String,
    val receiver: String,
    val amount: Long,
    val label: String,
    val createdAt: Long
){
    companion object {
        fun UserTransaction.toData() = TransactionEntity(
            sender = sender,
            receiver = receiver,
            amount = amount,
            label = label,
            createdAt = createdAt
        )
    }
}
