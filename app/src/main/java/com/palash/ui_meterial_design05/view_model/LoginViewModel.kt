package com.palash.ui_meterial_design05.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palash.ui_meterial_design05.model.login.request.LoginRequest
import com.palash.ui_meterial_design05.model.login.response.LoginResponse
import com.palash.ui_meterial_design05.model.signup.request.SignupRequest
import com.palash.ui_meterial_design05.model.signup.response.SignupResponse
import com.palash.ui_meterial_design05.repository.UserRepository
import com.palash.ui_meterial_design05.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val loginResLiveData: LiveData<NetworkResult<LoginResponse>> get() = userRepository.loginResLiveData
    val signupResLiveData: LiveData<NetworkResult<SignupResponse>> get() = userRepository.signupResLiveData

    fun loginRequest(loginRequest: LoginRequest) {
        viewModelScope.launch {
            userRepository.loginRequest(loginRequest)
        }
    }

    fun signupRequest(signupRequest: SignupRequest) {
        viewModelScope.launch {
            userRepository.signupRequest(signupRequest)
        }
    }
}