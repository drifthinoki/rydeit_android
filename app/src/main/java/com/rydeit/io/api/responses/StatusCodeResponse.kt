package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StatusCodeResponse(
    @SerializedName("status") var status:Status
){
    val isSuccess: Boolean
        get() = status.code == "0"
}