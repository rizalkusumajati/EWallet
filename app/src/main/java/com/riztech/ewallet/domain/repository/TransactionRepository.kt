package com.riztech.ewallet.domain.repository

import com.riztech.ewallet.data.local.db.TransactionEntity
import com.riztech.ewallet.domain.model.UserTransaction

interface TransactionRepository {
    suspend fun insertTransaction(userTransaction: UserTransaction)
    suspend fun getAllTransactionByUsername(username: String): List<UserTransaction>
}