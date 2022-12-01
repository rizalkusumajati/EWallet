package com.riztech.ewallet.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.ewallet.data.local.sharedpref.EwalletPref.clearValues
import com.riztech.ewallet.data.local.sharedpref.EwalletPref.userId
import com.riztech.ewallet.domain.model.User
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.domain.repository.TransactionRepository
import com.riztech.ewallet.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userRepository: UserRepository,
    val transactionRepository: TransactionRepository,
    val sharedPreferences: SharedPreferences,
    val dispatcher: CoroutineDispatcher
): ViewModel() {

    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private var _transactions = MutableStateFlow<List<UserTransaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    fun getUserLogin() {
        val username = sharedPreferences.userId
        username?.let {
            viewModelScope.launch(dispatcher) {
                val userLogin = userRepository.getUser(it)
                _user.value = userLogin
            }
        }
    }

    fun getAllTransactionByUsername(){
        val username = sharedPreferences.userId
        viewModelScope.launch(dispatcher){
            username?.let {
                val transactions = transactionRepository.getAllTransactionByUsername(username = it)
                _transactions.value = transactions
            }

        }
    }

    fun clearSharedPreverence(){
        sharedPreferences.clearValues
    }
}