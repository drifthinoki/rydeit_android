package com.rydeit.io.api

import com.google.gson.JsonObject
import com.rydeit.io.api.responses.*
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
    fun loginSendEmailVerifyCode(): Observable<VerifyCodeResponse>

    /**
     * 登入第二階段，發送簡訊驗證碼
     */
    @GET(Constants.API_LOGIN_VERIFY_PHONE)
    fun loginSendSMSVerifyCode(): Observable<VerifyCodeResponse>

    /**
     * 登入第二階段，檢查 google auth / 簡訊驗證碼 / email驗證碼
     */
    @FormUrlEncoded
    @POST(Constants.API_LOGIN_2FA)
    fun login2FA(
        @Field("emailToken") emailVerifyCode: String,
        @Field("phoneToken") smsVerifyCode: String,
        @Field("token2fa") google2fa: String
    ): Observable<TokenResponse>
    // endregion

    // region 忘記密碼
    /**
     * 未登入, 發送信箱驗證碼
     */
    @GET(Constants.API_RESET_SEND_VERIFY_EMAIL)
    fun resetSendEmailVerifyCode(@Path("account") email: String
    ): Observable<JsonObject>

    /**
     * 忘記密碼第一階段, 檢查信箱驗證碼是否正確
     */
    @FormUrlEncoded
    @POST(Constants.API_RESET_VERIFY_EMAIL)
    fun resetVerifyEmailCode(
        @Field("account") email: String,
        @Field("emailToken") emailVerifyCode: String
    ): Observable<TokenResponse>

    /**
     * 忘記密碼第二階段, 重設密碼
     */
    @FormUrlEncoded
    @POST(Constants.API_RESET_PASSWORD)
    fun resetPassword(
        @Field("password") password: String
    ): Observable<StatusCodeResponse>
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
    ) :Observable<TokenResponse>

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
        @Query("referral") referral: String? = null
    ): Observable<StatusCodeResponse>

    // endregion
}