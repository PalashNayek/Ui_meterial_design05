package com.palash.ui_meterial_design05.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.palash.ui_meterial_design05.api.UnauthorizedAPI
import com.palash.ui_meterial_design05.model.login.request.LoginRequest
import com.palash.ui_meterial_design05.model.login.response.LoginResponse
import com.palash.ui_meterial_design05.model.signup.request.SignupRequest
import com.palash.ui_meterial_design05.model.signup.response.SignupResponse
import com.palash.ui_meterial_design05.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val unauthorizedAPI: UnauthorizedAPI) {

    private var _loginResponseMutableLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResLiveData: LiveData<NetworkResult<LoginResponse>> get() = _loginResponseMutableLiveData

    suspend fun loginRequest(loginRequest: LoginRequest) {
        val response = unauthorizedAPI.getLoginResponse(loginRequest)
        if (response.isSuccessful && response.body() != null) {
            _loginResponseMutableLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _loginResponseMutableLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _loginResponseMutableLiveData.postValue(NetworkResult.Error("Something went to wrong"))
        }
    }

    private var _signupResponseMutableLiveData = MutableLiveData<NetworkResult<SignupResponse>>()
    val signupResLiveData: LiveData<NetworkResult<SignupResponse>> get() = _signupResponseMutableLiveData

    suspend fun signupRequest(signupRequest: SignupRequest) {
        val response = unauthorizedAPI.getSignupResponse(signupRequest)
        if (response.isSuccessful && response.body() != null) {
            _signupResponseMutableLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _signupResponseMutableLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _signupResponseMutableLiveData.postValue(NetworkResult.Error("Something went to wrong"))
        }
    }
}