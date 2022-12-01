package com.riztech.ewallet.data.local.db

import com.riztech.ewallet.data.local.db.TransactionEntity.Companion.toDomain
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.presentation.Constants
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class TransactionEntityTest {

    private lateinit var transactionEntity: TransactionEntity

    @Before
    fun setup(){
        transactionEntity = TransactionEntity(
            sender = "Bob",
            receiver = "Alice",
            amount = 20000,
            label = Constants.LABEL_TOPUP,
            createdAt = System.currentTimeMillis()/1000
        )
    }

    @Test
    fun `check fun toDomain return UserTransaction object`() {
        //Given
        val localTransactionEntity = transactionEntity
        //When
        val userTransaction = localTransactionEntity.toDomain()
        //Then
        assertEquals(expected = UserTransaction::class, actual = userTransaction::class)
    }

}