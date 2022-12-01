package com.riztech.ewallet.presentation.transfer

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
class TransferViewModel @Inject constructor(
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

    fun transferTransaction(receiver: String, amount: Long, createdAt: Long){
        viewModelScope.launch(dispatcher) {
            user.value?.let {
                if (receiver.isNullOrBlank()){
                    _navigate.value = "error_receiver_blank"
                }else{
                    if (it.amount < amount){
                        _navigate.value = "error_amount"
                    }else {
                        val receiverDatabase = userRepository.getRegisteredUser(receiver.lowercase())
                        if (receiverDatabase.isNullOrBlank().not()){
                            val receiverUser = userRepository.getUser(receiverDatabase)
                            transactionRepository.insertTransaction(
                                UserTransaction(
                                    sender = it.username,
                                    receiver = receiver,
                                    amount = amount,
                                    label = Constants.LABEL_TRANSFER,
                                    createdAt = createdAt
                                )
                            )
                            val deductAmount = it.amount - amount
                            userRepository.updateAmount(username = it.username, amount = deductAmount)
                            val incomeAMount = receiverUser.amount + amount
                            userRepository.updateAmount(username = receiverDatabase, amount = incomeAMount)
                            _navigate.value = "home"

                        }else {
                            _navigate.value = "error_receiver"
                        }

                    }
                }

            }
        }

    }

}