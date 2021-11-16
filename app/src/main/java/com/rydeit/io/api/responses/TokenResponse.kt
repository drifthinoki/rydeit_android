package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class TokenResponse(
    @SerializedName("data") var data:DataLogin2fa,
    @SerializedName("status") var status:StatusLogin2fa
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class DataLogin2fa (
    @SerializedName("token") var token:String
)

@Keep
data class StatusLogin2fa (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)
