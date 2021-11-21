package com.rydeit.io.ui.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.Plan
import com.rydeit.io.helper.UserHelper

class PurchaseViewModel: ViewModel() {

    private val _planList = MutableLiveData<List<Plan>>()
    val planList: LiveData<List<Plan>> = _planList
    var planNameList: List<String>? = null
    var isApiSuccess = false

    init {
        getPlanList()
    }

    private fun getPlanList() {
        RetrofitServiceManager.apiService.getPlanList()
            .subscribe { response ->
//                if (Constants.DEBUG) Log.e(TAG, response.toString())
                isApiSuccess = true
                _planList.postValue(response.data)
                planNameList = response.data.map {
                    it.name
                }
            }
    }

}