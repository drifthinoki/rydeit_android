package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    @SerializedName("data") var data:Data,
    @SerializedName("status") var status:Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class Data (
    @SerializedName("token") var token:String,
    @SerializedName("auth") var isNeedGoogleAuthVerify:Boolean,
    @SerializedName("email") var isNeedEmailVerify:Boolean,
    @SerializedName("sms") var isNeedSMSVerify:Boolean,
    @SerializedName("phoneConfrimed") var isPhone:Boolean
)

@Keep
data class Status (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)