package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User

class RegisterStep1ViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    private val inputCheckItem = InputCheckItem(3)
    var isInputValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isRegisterSuccess: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun updateInputValue(inputTextIndex: Int, value: String) {
        inputCheckItem.updateValue(inputTextIndex, value)
        isInputValid.postValue(inputCheckItem.isValid())
    }

    fun register(email: String, phone: String) {
        if (Constants.DEBUG && Constants.API_SUCCESS) {
            isRegisterSuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .registerStep1(email, phone)
            .subscribe({ registerStep1 ->
                val user = User(email, registerStep1.data.token)
                UserHelper.setUser(user)
                isRegisterSuccess.postValue(registerStep1.isSuccess)
            }, {
                Log.e(TAG, "register step1 api failed!")
                isRegisterSuccess.postValue(false)
            })
    }

}

class InputCheckItem(numberOfValues:Int) {

    private val TAG = this::class.java.simpleName

    private var valueList:MutableList<String> = mutableListOf()

    init {
        for (i in 0 until numberOfValues) {
            valueList.add(i, "")
        }
    }

    fun updateValue(index: Int, value: String) {
        valueList[index] = value
    }

    fun isValid():Boolean {
//        if (Constants.DEBUG) Log.e(TAG, "$valueList")
        return valueList.all { it.isNotEmpty() }
    }

    fun getValue(index: Int): String {
        return valueList[index]
    }
}

