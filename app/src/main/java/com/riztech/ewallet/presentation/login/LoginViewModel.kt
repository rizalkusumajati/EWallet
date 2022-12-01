package com.riztech.ewallet.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riztech.ewallet.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val dispatcher: CoroutineDispatcher
):ViewModel(){

    private var _readyToGo = MutableSharedFlow<Boolean>()
    val readyToGo = _readyToGo.asSharedFlow()

    fun login(username: String){
        viewModelScope.launch(dispatcher) {
            val userRegistered = userRepository.getRegisteredUser(username.lowercase())
            if (userRegistered.isBlank()){
                userRepository.insertNewUser(username)
            }

            userRepository.insertSharedPref(username.lowercase())
            _readyToGo.emit(true)
        }
    }

}