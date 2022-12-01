package com.riztech.ewallet.domain.model

import com.riztech.ewallet.data.local.db.TransactionEntity
import com.riztech.ewallet.data.local.db.TransactionEntity.Companion.toDomain
import com.riztech.ewallet.domain.model.UserTransaction.Companion.toData
import com.riztech.ewallet.presentation.Constants
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


internal class UserTransactionTest{
    private lateinit var userTransaction: UserTransaction

    @Before
    fun setup(){
        userTransaction = UserTransaction(
            sender = "Bob",
            receiver = "Alice",
            amount = 20000,
            label = Constants.LABEL_TOPUP,
            createdAt = System.currentTimeMillis()/1000
        )
    }

    @Test
    fun `check fun toData return TransactionEntity object`(){
        //Given
        val localUserTransaction = userTransaction
        //When
        val userTransaction = localUserTransaction.toData()
        //Then
        assertEquals(expected = TransactionEntity::class, userTransaction::class)
    }

}