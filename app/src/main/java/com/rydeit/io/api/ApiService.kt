package com.rydeit.io.api

import com.google.gson.JsonObject
import com.rydeit.io.api.responses.Login
import com.rydeit.io.api.responses.Login2fa
import com.rydeit.io.api.responses.RegisterStep1
import com.rydeit.io.config.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {

    // region 登入
    /**
     * 登入第一階段
     */
    @FormUrlEncoded
    @POST(Constants.API_LOGIN)
    fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): Observable<Login>

    /**
     * 發送信箱驗證碼
     */
    @GET(Constants.API_LOGIN_SEND_EMAIL_VERIFY_CODE)
    fun sendEmailVerifyCode(): Observable<JsonObject>

    /**
     * 發送簡訊驗證碼
     */
    @GET(Constants.API_LOGIN_SEND_SMS_VERIFY_CODE)
    fun sendSMSVerifyCode(): Observable<JsonObject>

    /**
     * 登入第二階段，檢查 google auth / 簡訊驗證碼 / email驗證碼
     */
    @FormUrlEncoded
    @POST(Constants.API_LOGIN_2FA)
    fun login2FA(
        @Field("emailToken") emailVerifyCode: String,
        @Field("phoneToken") smsVerifyCode: String,
        @Field("token2fa") google2fa: String
    ): Observable<Login2fa>
    // endregion

    // region 註冊
    /**
     * 註冊第一階段，成功後返回 jwt token
     */
    @FormUrlEncoded
    @POST(Constants.API_REGISTER_STEP_1)
    fun registerStep1(
        @Field("account") email: String,
        @Field("phone") phone: String
    ) :Observable<RegisterStep1>
    // endregion
}