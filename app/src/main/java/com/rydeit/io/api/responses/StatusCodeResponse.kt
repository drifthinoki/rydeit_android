package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StatusCodeResponse(
    @SerializedName("status") var status:RegisterStep3Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class RegisterStep3Status (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)