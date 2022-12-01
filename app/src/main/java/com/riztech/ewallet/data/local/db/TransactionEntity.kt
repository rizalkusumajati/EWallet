package com.riztech.ewallet.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riztech.ewallet.domain.model.UserTransaction

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sender: String,
    val receiver: String,
    val amount: Long,
    val label: String,
    val createdAt: Long
){
    companion object{
        fun TransactionEntity.toDomain(): UserTransaction{
            return UserTransaction(
                sender = sender,
                receiver = receiver,
                amount = amount,
                label = label,
                createdAt = createdAt
            )
        }
    }
}
