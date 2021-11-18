package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User


class ForgetPasswordViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    private val inputCheckItem = InputCheckItem(2)
    var isInputValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isVerifySuccess: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun updateInputValue(inputTextIndex: Int, value: String) {
        inputCheckItem.updateValue(inputTextIndex, value)
        isInputValid.postValue(inputCheckItem.isValid())
    }

    fun verifyEmail(email: String) {
        RetrofitServiceManager.apiService
            .resetSendEmailVerifyCode(email)
            .subscribe{ response ->
                if (Constants.DEBUG) Log.e(TAG, response.verifyCode)
            }
    }

    fun verifyEmailCode(email: String, code: String) {
        if (Constants.DEBUG && Constants.API_SUCCESS) {
            isVerifySuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .resetVerifyEmailCode(email, code)
            .subscribe({ response ->
                val user = User(email, response.data.token)
                UserHelper.setUser(user)
                isVerifySuccess.postValue(response.isSuccess)
            }, {
                Log.e(TAG, "reset verify email code api failed!")
                isVerifySuccess.postValue(false)
            })

    }

}

