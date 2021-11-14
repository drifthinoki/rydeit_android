package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterStep2(
    @SerializedName("data") var data:RegisterStep2Data,
    @SerializedName("status") var status:RegisterStep2Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class RegisterStep2Data(
    @SerializedName("emailPass") var emailVerified:Boolean,
    @SerializedName("phonePass") var phoneVerified:Boolean
)

@Keep
data class RegisterStep2Status (
    @SerializedName("code") var code:String,
    @SerializedName("message") var message:String,
    @SerializedName("datetime") var datetime:String
)