package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants

class RegisterStep2ViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    private val inputCheckItem = InputCheckItem(2)
    var isInputValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isVerifySuccess: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun updateInputValue(inputTextIndex: Int, value: String) {
        inputCheckItem.updateValue(inputTextIndex, value)
        isInputValid.postValue(inputCheckItem.isValid())
    }


    fun verifyEmail() {
        RetrofitServiceManager.apiService
            .registerSendEmailVerifyCode()
            .subscribe ()
    }

    fun verifyPhone() {
        RetrofitServiceManager.apiService
            .registerSendSMSVerifyCode()
            .subscribe ()
    }

    fun registerStep2(emailVerifyCode: String, smsVerifyCode: String) {
        if (Constants.DEBUG && Constants.API_SUCCESS) {
            isVerifySuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .registerStep2(emailVerifyCode, smsVerifyCode)
            .subscribe({ registerStep2 ->
                isVerifySuccess.postValue(registerStep2.isSuccess)
            }, {
                Log.e(TAG, "register step2 api failed!")
                isVerifySuccess.postValue(false)
            })
    }
}


