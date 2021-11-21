package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.Plan
import com.rydeit.io.config.Constants
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User
import com.rydeit.io.ui.adapter.CurrencyType
import com.rydeit.io.ui.adapter.SelectCurrencyItem
import com.rydeit.io.ui.adapter.SelectablePlanItem
import com.rydeit.io.ui.customview.ListBottomSheetType

class MainViewModel():ViewModel() {

    private val TAG = this::class.java.simpleName
    var isLogin: MutableLiveData<Boolean> = UserHelper.isLogin

    fun getUser():User? {
        return UserHelper.getUser()
    }

    private val _planListLiveData = MutableLiveData<List<Plan>>()
    val planListLiveData: LiveData<List<Plan>> = _planListLiveData
    private var planList: List<Plan>? = null

    // Bottom sheet
    var userPurchaseItem = PurchaseItem()
    var selectablePlanList: List<SelectablePlanItem> = emptyList()
    val selectableCurrencyList = listOf(CurrencyType.USDT, CurrencyType.USDC, CurrencyType.ETH).map { SelectCurrencyItem(it) }


    init {
        getPlanList()
    }

    private fun getPlanList() {
        RetrofitServiceManager.apiService.getPlanList()
            .subscribe { response ->
//                if (Constants.DEBUG) Log.e(TAG, response.toString())
                _planListLiveData.postValue(response.data)
                planList = response.data
                selectablePlanList = response.data
                    .filterNot { it.stage == 3 }
                    .map {
                        val formatName = if (it.stage == 1) {
                            it.name
                        }else {
                            it.name + " ( 已額滿 )"
                        }
                        SelectablePlanItem(formatName, it.stage == 1)
                    }
            }
    }

    fun updateUserPurchaseItem(listType: ListBottomSheetType, index: Int) {
        if (planList.isNullOrEmpty()) throw IllegalStateException()
        when(listType) {
            ListBottomSheetType.ChoosePlan -> userPurchaseItem.plan = planList!![index]
            ListBottomSheetType.ChooseCurrency -> userPurchaseItem.currencyType = selectableCurrencyList[index].type
        }
        if (Constants.DEBUG) Log.e(TAG, "user purchase item: $userPurchaseItem")
    }


    fun clearUserPurchaseChoice() {
        userPurchaseItem = PurchaseItem()
        selectablePlanList.forEach { it.isSelected = false }
        selectableCurrencyList.forEach { it.isSelected = false }
    }


}

data class PurchaseItem(var plan: Plan? = null, var currencyType: CurrencyType? = null, val amount:Int? = null)
