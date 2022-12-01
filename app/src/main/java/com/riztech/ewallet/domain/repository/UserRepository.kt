package com.riztech.ewallet.domain.repository

import com.riztech.ewallet.domain.model.User

interface UserRepository {
    suspend fun insertNewUser(username: String)
    suspend fun getRegisteredUser(username:String): String
    fun insertSharedPref(username: String)
    suspend fun getUser(username: String): User
    suspend fun updateAmount(username: String, amount: Long)
}