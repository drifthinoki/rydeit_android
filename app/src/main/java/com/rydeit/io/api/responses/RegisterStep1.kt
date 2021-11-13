package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterStep1(
    @SerializedName("data") var data:RegisterData,
    @SerializedName("status") var status:RegisterStatus
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class RegisterData(
    @SerializedName("token") var token:String
)

@Keep
data class RegisterStatus (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)