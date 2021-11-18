package com.rydeit.io.api.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.rydeit.io.ui.adapter.PlanCurrencyType
import java.text.NumberFormat

@Keep
data class PlanResponse(
    @SerializedName("data") var data:List<Plan>,
    @SerializedName("status") var status:Status
)

/**
 * @param stage 1:可購買, 2:進行中方案，未開放申購, 3:該方案未開始申購
 * @param apy_type 1:年化報酬, 2:績效
 * @param car_type 該方案代碼(申購時使用)
 * @param progressPercent 百分比 0 - 1 float
 *
 */
@Keep
data class Plan(
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("apy") var rateOfReturn: String,
    @SerializedName("apy_type") var rateOfReturnType: Int,
    @SerializedName("progress") var progressPercent: Float,
    @SerializedName("max") var progressMax: Int,
    @SerializedName("need_progress") var needProgress: Boolean,
    @SerializedName("stage") var stage: Int,
    @SerializedName("currency") var currency: List<String>,
    @SerializedName("car_type") var carType: String,
    @SerializedName("intro") var path: String,
    @SerializedName("url") var baseUrl: String) {

    private val amountFormatter: NumberFormat
        get() = NumberFormat.getInstance().apply {
            minimumFractionDigits = 4
            maximumFractionDigits = 4
         }

    val webUrl: String
        get() = baseUrl + path

    val planCurrencyTypeList: List<PlanCurrencyType>
        get() {
            return currency.map {
                val type = PlanCurrencyType.valueOf(it)
                return@map type
            }
        }

    // progressMax * 進度的百分比 才能得到實際的進度
    private val progressAmount:Float
        get() = progressMax * progressPercent

    // 特殊規則 進度100時顯示0, 其餘正常顯示
    val progressForProgressBar: Int
        get() {
            return if (progressPercent == 1.0f) 0 else progressAmount.toInt()
        }
    val progressString: String
        get() {
            return amountFormatter.format(progressAmount) + " / " + amountFormatter.format(progressMax) + " " + currency.first()
        }

    val formatProgressPercent: String
        get() = (progressPercent * 100).toInt().toString() + "%"


}
