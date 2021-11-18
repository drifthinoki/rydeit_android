package com.rydeit.io.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.Plan
import com.rydeit.io.config.Constants
import com.rydeit.io.helper.UserHelper

class HomeViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    private val _planList = MutableLiveData<List<Plan>>()
    val planList: LiveData<List<Plan>> = _planList
    val isLogin: MutableLiveData<Boolean> = UserHelper.isLogin

    init {
        getPlanList()
    }

    fun getPlanList() {
        RetrofitServiceManager.apiService.getPlanList()
            .subscribe { response ->
                if (Constants.DEBUG) Log.e(TAG, response.toString())
                _planList.postValue(response.data)
            }
    }
}