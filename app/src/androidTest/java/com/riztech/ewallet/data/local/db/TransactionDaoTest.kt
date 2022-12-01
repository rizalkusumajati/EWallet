package com.riztech.ewallet.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.presentation.Constants
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransactionDaoTest {
    private lateinit var eWalletDb: EWalletDb
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        eWalletDb = Room.inMemoryDatabaseBuilder(context, EWalletDb::class.java).build()
        transactionDao = eWalletDb.transactionDao()
    }

    @After
    fun tearDown(){
        eWalletDb.close()
    }

    @Test
    fun insertTransactionAndGetAll(){
        val userTransaction = TransactionEntity(
            id = 1,
            sender = "bob",
            receiver = "alice",
            amount = 100,
            label = Constants.LABEL_TRANSFER,
            createdAt = System.currentTimeMillis()/1000
        )

        runBlocking {
            transactionDao.insertAll(userTransaction)
            val transactions = transactionDao.getAllTransactionByUsername("bob")
            println("data : ${transactions}")
            assert(transactions.contains(userTransaction))
        }
    }

}