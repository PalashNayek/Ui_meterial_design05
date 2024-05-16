package com.palash.ui_meterial_design05.api

import com.palash.ui_meterial_design05.model.login.request.LoginRequest
import com.palash.ui_meterial_design05.model.login.response.LoginResponse
import com.palash.ui_meterial_design05.model.signup.request.SignupRequest
import com.palash.ui_meterial_design05.model.signup.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UnauthorizedAPI {

    @POST("auth/login")
    suspend fun getLoginResponse(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("users/add")
    suspend fun getSignupResponse(@Body signupRequest: SignupRequest) : Response<SignupResponse>
}