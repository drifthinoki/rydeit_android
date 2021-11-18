package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class TokenResponse(
    @SerializedName("data") var data:DataLogin2fa,
    @SerializedName("status") var status:Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class DataLogin2fa (
    @SerializedName("token") var token:String
)

