package com.riztech.ewallet.presentation.topup

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.ewallet.data.local.sharedpref.EwalletPref.userId
import com.riztech.ewallet.domain.model.User
import com.riztech.ewallet.domain.model.UserTransaction
import com.riztech.ewallet.domain.repository.TransactionRepository
import com.riztech.ewallet.domain.repository.UserRepository
import com.riztech.ewallet.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    val userRepository: UserRepository,
    val transactionRepository: TransactionRepository,
    val dispatcher: CoroutineDispatcher,
    val sharedPreferences: SharedPreferences
): ViewModel() {

    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private var _navigate = MutableStateFlow<String?>(null)
    val navigate = _navigate.asStateFlow()


    fun getUserLogin() {
        val username = sharedPreferences.userId
        username?.let {
            viewModelScope.launch(dispatcher) {
                val userLogin = userRepository.getUser(it)
                _user.value = userLogin
            }
        }
    }

    fun topUpTransaction(amount: Long, createdAt: Long){
        viewModelScope.launch(dispatcher) {
            if (amount <= 0){
                _navigate.value = "error_amount"
            }else{
                user.value?.let {
                    transactionRepository.insertTransaction(
                        UserTransaction(
                            sender = it.username,
                            receiver = "",
                            amount = amount,
                            label = Constants.LABEL_TOPUP,
                            createdAt = createdAt
                        )
                    )
                    val newAmount = it.amount + amount
                    userRepository.updateAmount(username = it.username, amount = newAmount)
                    _navigate.value = "home"
                }
            }
        }

    }
}