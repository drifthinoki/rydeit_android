package com.rydeit.io.api

import com.google.gson.JsonObject
import com.rydeit.io.api.responses.Login
import com.rydeit.io.api.responses.Login2fa
import com.rydeit.io.api.responses.RegisterStep1
import com.rydeit.io.api.responses.RegisterStep2
import com.rydeit.io.config.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {

    // region 登入
    /**
     * 登入第一階段，成功後返回 jwt token
     */
    @FormUrlEncoded
    @POST(Constants.API_LOGIN)
    fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): Observable<Login>

    /**
     * 登入第二階段，發送信箱驗證碼
     */
    @GET(Constants.API_LOGIN_VERIFY_EMAIL)
    fun loginSendEmailVerifyCode(): Observable<JsonObject>

    /**
     * 登入第二階段，發送簡訊驗證碼
     */
    @GET(Constants.API_LOGIN_VERIFY_PHONE)
    fun loginSendSMSVerifyCode(): Observable<JsonObject>

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

    /**
     * 註冊第二階段，發出驗證email
     */
    @GET(Constants.API_REGISTER_VERIFY_EMAIL)
    fun registerSendEmailVerifyCode() :Observable<JsonObject>

    /**
     * 註冊第二階段，發出驗證簡訊
     */
    @GET(Constants.API_REGISTER_VERIFY_PHONE)
    fun registerSendSMSVerifyCode() :Observable<JsonObject>

    /**
     * 註冊第二階段，檢查 簡訊驗證碼 / email驗證碼
     */
    @FormUrlEncoded
    @POST(Constants.API_REGISTER_STEP_2)
    fun registerStep2(
        @Field("emailToken") emailVerifyCode: String,
        @Field("phoneToken") smsVerifyCode: String
    ): Observable<RegisterStep2>

    /**
     * 註冊第三階段，上傳 user 暱稱 / 密碼 / 推薦碼
     */
    @FormUrlEncoded
    @POST(Constants.API_REGISTER_STEP_3)
    fun registerStep3(
        @Field("name") nickname: String,
        @Field("password") password: String,
        @Field("referral") referral: String
    ): Observable<JsonObject>

    // endregion
}