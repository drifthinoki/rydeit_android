package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyCodeResponse(
    @SerializedName("data") var verifyCode: String,
    @SerializedName("status") var status:Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}