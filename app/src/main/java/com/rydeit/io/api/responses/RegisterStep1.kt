package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterStep1(
    @SerializedName("data") var data:RegisterStep1Data,
    @SerializedName("status") var status:RegisterStep1Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class RegisterStep1Data(
    @SerializedName("token") var token:String
)

@Keep
data class RegisterStep1Status (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)