package com.riztech.ewallet.data.local.db

import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM transactionentity WHERE sender = :username ORDER BY createdAt DESC")
    suspend fun getAllTransactionByUsername(username: String): List<TransactionEntity>
}