package com.riztech.ewallet.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.riztech.ewallet.data.local.db.EWalletDb
import com.riztech.ewallet.data.local.db.TransactionDao
import com.riztech.ewallet.data.local.db.TransactionEntity
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.presentation.Constants
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransactionRepositoryImplTest {

    private lateinit var eWalletDb: EWalletDb
    private lateinit var transactionDao: TransactionDao
    private lateinit var transactionRepositoryImpl: TransactionRepositoryImpl

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        eWalletDb = Room.inMemoryDatabaseBuilder(context, EWalletDb::class.java).build()
        transactionDao = eWalletDb.transactionDao()
        transactionRepositoryImpl = TransactionRepositoryImpl(
            transactionDao =  transactionDao
        )
    }

    @After
    fun tearDown(){
        eWalletDb.close()
    }

    @Test
    fun checkInsertTransactionAndGetTransactionByUsername() {
        val userTransaction = UserTransaction(
            sender = "bob",
            receiver = "alice",
            amount = 100,
            label = Constants.LABEL_TRANSFER,
            createdAt = System.currentTimeMillis()/1000
        )
        runBlocking {
            transactionRepositoryImpl.insertTransaction(userTransaction)
            val transactions = transactionDao.getAllTransactionByUsername("bob")
            val testTransaction= transactions[0]
            assert(testTransaction.sender.equals(userTransaction.sender))
            assert(testTransaction.amount == userTransaction.amount)
            assert(testTransaction.receiver.equals(userTransaction.receiver))
            assert(testTransaction.label.equals(userTransaction.label))
            assert(testTransaction.createdAt == userTransaction.createdAt)
        }
    }

}