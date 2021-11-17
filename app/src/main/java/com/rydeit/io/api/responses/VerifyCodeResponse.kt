package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyCodeResponse(
    @SerializedName("data") var verifyCode: String,
    @SerializedName("status") var status:StatusVerifyCode
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class StatusVerifyCode (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)