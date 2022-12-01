package com.riztech.ewallet.data.di

import com.riztech.ewallet.data.repository.TransactionRepositoryImpl
import com.riztech.ewallet.data.repository.UserRepositoryImpl
import com.riztech.ewallet.domain.repository.TransactionRepository
import com.riztech.ewallet.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun provideTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}