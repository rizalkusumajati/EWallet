package com.riztech.ewallet.data.local.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM userentity WHERE username=:username")
    suspend fun getUser(username: String): UserEntity

    @Transaction
    @Query("UPDATE userentity SET amount = :amount WHERE username = :username")
    suspend fun updateAmount(username: String, amount: Long)
}