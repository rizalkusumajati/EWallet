package com.riztech.ewallet.data.local.db

import com.riztech.ewallet.data.local.db.UserEntity.Companion.toDomain
import com.riztech.ewallet.domain.model.User
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserEntityTest {
    private lateinit var userEntity: UserEntity

    @Before
    fun setup(){
        userEntity = UserEntity(
            username = "alice",
            name = "Alice",
            amount = 0,
            createdAt = System.currentTimeMillis()/1000
        )
    }

    @Test
    fun `check fun toDomain return User object`(){
        //Given
        val localUserEntity = userEntity
        //When
        val user = localUserEntity.toDomain()
        //Then
        assertEquals(User::class, user::class)
    }
}