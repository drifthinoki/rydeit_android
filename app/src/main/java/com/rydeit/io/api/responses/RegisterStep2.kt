package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterStep2(
    @SerializedName("data") var data:RegisterStep2Data,
    @SerializedName("status") var status:Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}

@Keep
data class RegisterStep2Data(
    @SerializedName("emailPass") var emailVerified:Boolean,
    @SerializedName("phonePass") var phoneVerified:Boolean
)
