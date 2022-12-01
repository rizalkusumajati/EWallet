package com.riztech.ewallet.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.riztech.ewallet.data.local.db.EWalletDb
import com.riztech.ewallet.data.local.db.UserDao
import com.riztech.ewallet.data.local.db.UserEntity
import com.riztech.ewallet.domain.model.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private lateinit var eWalletDb: EWalletDb
    private lateinit var userDao: UserDao
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        eWalletDb = Room.inMemoryDatabaseBuilder(context, EWalletDb::class.java).build()
        userDao = eWalletDb.userDao()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        userRepositoryImpl = UserRepositoryImpl(
            userDao = userDao,
            pref = sharedPreferences
        )
    }

    @After
    fun tearDown(){
        eWalletDb.close()
    }

    @Test
    fun checkInsertNewUserAndGetRegisteredUser(){
        val userEntity = UserEntity(
            username = "alice",
            name = "Alice",
            amount = 0,
            createdAt = System.currentTimeMillis()
        )

        runBlocking {
            userRepositoryImpl.insertNewUser("Alice")
            val registerUser = userRepositoryImpl.getRegisteredUser("alice")
            assert(registerUser.equals(userEntity.username))
        }
    }

    @Test
    fun checkGetUserReturnUserObject(){

        runBlocking {
            userRepositoryImpl.insertNewUser("Alice")
            val registerUser = userRepositoryImpl.getUser("alice")
            assert(registerUser::class.java == User::class.java)
        }
    }

    @Test
    fun checkUpdateAmount(){

        runBlocking {
            userRepositoryImpl.insertNewUser("Alice")
            userRepositoryImpl.updateAmount("alice", 100)
            val registerUser = userRepositoryImpl.getUser("alice")

            assert(registerUser.amount == 100L)
        }
    }
}