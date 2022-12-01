package com.riztech.ewallet.data.repository

import android.content.SharedPreferences
import com.riztech.ewallet.data.local.db.UserDao
import com.riztech.ewallet.data.local.db.UserEntity
import com.riztech.ewallet.data.local.db.UserEntity.Companion.toDomain
import com.riztech.ewallet.data.local.sharedpref.EwalletPref
import com.riztech.ewallet.data.local.sharedpref.EwalletPref.userId
import com.riztech.ewallet.domain.model.User
import com.riztech.ewallet.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val pref: SharedPreferences
): UserRepository {
    override suspend fun insertNewUser(username: String) {
        userDao.insertAll(UserEntity(
            username = username.lowercase(),
            name = username,
            amount = 0,
            createdAt = System.currentTimeMillis()/1000
        ))
    }

    override suspend fun getRegisteredUser(username: String): String {
        return userDao.getUser(username)?.username ?: ""
    }

    override fun insertSharedPref(username: String) {
        pref.userId = username
    }

    override suspend fun getUser(username: String): User {
        return userDao.getUser(username)?.toDomain()
    }

    override suspend fun updateAmount(username: String, amount: Long) {
        userDao.updateAmount(username, amount)
    }
}