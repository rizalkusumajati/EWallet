package com.riztech.ewallet.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserDaoTest {

    private lateinit var eWalletDb: EWalletDb
    private lateinit var userDao: UserDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        eWalletDb = Room.inMemoryDatabaseBuilder(context, EWalletDb::class.java).build()
        userDao = eWalletDb.userDao()
    }

    @After
    fun tearDown(){
        eWalletDb.close()
    }

    @Test
    fun checkInsertUserAndGetUser() {
        val userEntity = UserEntity(
            username = "alice",
            name = "Alice",
            amount = 0,
            createdAt = System.currentTimeMillis()
        )

        runBlocking {
            userDao.insertAll(userEntity)
            val userAlice = userDao.getUser("alice")
            assert(userAlice.username.equals(userEntity.username))
        }
    }

    @Test
    fun insertUserAndUpdateAmount(){
        val userEntity = UserEntity(
            username = "alice",
            name = "Alice",
            amount = 0,
            createdAt = System.currentTimeMillis()/1000
        )

        runBlocking {
            userDao.insertAll(userEntity)
            val userAlice = userDao.getUser("alice")
            assert(userAlice.amount == 0L)

            userDao.updateAmount("alice", 100)
            val userAliceUpdate = userDao.getUser("alice")
            println("Amount Update : ${userAliceUpdate.amount}")
            assert(userAliceUpdate.amount == 100L)
        }
    }

}