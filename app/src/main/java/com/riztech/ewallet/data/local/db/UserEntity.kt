package com.riztech.ewallet.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riztech.ewallet.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey
    val username: String,
    val name: String,
    val amount: Long,
    val createdAt: Long
){
    companion object{
        fun UserEntity.toDomain() = User(
            username = username,
            name = name,
            amount = amount
        )
    }
}
