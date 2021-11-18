package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.rydeit.io.ui.adapter.PlanCurrencyType

@Keep
data class PlanResponse(
    @SerializedName("data") var data:List<Plan>,
    @SerializedName("status") var status:Status
)

/**
 * @param stage 1:可購買, 2:進行中方案，未開放申購, 3:該方案未開始申購
 * @param apy_type 1:年化報酬, 2:績效
 * @param car_type 該方案代碼(申購時使用)
 *
 */
@Keep
data class Plan(
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("apy") var rateOfReturn: String,
    @SerializedName("apy_type") var rateOfReturnType: Int,
    @SerializedName("progress") var progress: Int,
    @SerializedName("max") var progressMax: Int,
    @SerializedName("need_progress") var needProgress: Boolean,
    @SerializedName("stage") var stage: Int,
    @SerializedName("currency") var currency: List<String>,
    @SerializedName("car_type") var carType: String,
    @SerializedName("intro") var path: String,
    @SerializedName("url") var baseUrl: String) {

//    val url = baseUrl + path
    val planCurrencyTypeList: List<PlanCurrencyType>
        get() {
            return currency.map {
                val type = PlanCurrencyType.valueOf(it)
                return@map type
            }
        }


}
