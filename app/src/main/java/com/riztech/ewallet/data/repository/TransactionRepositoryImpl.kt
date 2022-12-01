package com.riztech.ewallet.data.repository

import com.riztech.ewallet.data.local.db.TransactionDao
import com.riztech.ewallet.data.local.db.TransactionEntity
import com.riztech.ewallet.data.local.db.TransactionEntity.Companion.toDomain
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.domain.model.UserTransaction.Companion.toData
import com.riztech.ewallet.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    val transactionDao: TransactionDao,
): TransactionRepository {
    override suspend fun insertTransaction(userTransaction: UserTransaction) {
        transactionDao.insertAll(userTransaction.toData())
    }

    override suspend fun getAllTransactionByUsername(username: String): List<UserTransaction> {
        return transactionDao.getAllTransactionByUsername(username).map { it.toDomain() }
    }
}