package com.riztech.ewallet.data.di

import android.content.Context
import androidx.room.Room
import com.riztech.ewallet.data.local.db.EWalletDb
import com.riztech.ewallet.data.local.db.TransactionDao
import com.riztech.ewallet.data.local.db.UserDao
import com.riztech.ewallet.data.local.sharedpref.EwalletPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EWalletDb{
        return Room.databaseBuilder(
           context,
            EWalletDb::class.java,
            "ewallet.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: EWalletDb): UserDao{
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: EWalletDb): TransactionDao{
        return db.transactionDao()
    }

    @Provides
    @Singleton
    fun provideEwalletPref(): EwalletPref {
        return EwalletPref
    }

}