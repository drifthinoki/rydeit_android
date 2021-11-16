package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants
import java.util.regex.Pattern

class ResetPasswordViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    private val inputCheckItem = InputCheckItem(2)
    var isInputValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isVerifySuccess: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isPasswordLengthValid: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isPasswordSymbolValid: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isConfirmPasswordValid: MutableLiveData<Boolean?> = MutableLiveData(null)
    private val pattern: Pattern = Pattern.compile(Constants.PASSWORD_REGEX)

    fun checkInputValue(inputTextIndex: Int, value: String) {
        when(inputTextIndex) {
            1 -> updateInputValue(inputTextIndex, value)
            0 -> {
                //檢查密碼長度
                val lengthOK = value.length >= 8
                isPasswordLengthValid.postValue(lengthOK)
                if (Constants.DEBUG) Log.e(TAG, "isPasswordLengthValid: $lengthOK")

                //檢查密碼字元
                val matcher = pattern.matcher(value)
                val symbolOK = matcher.matches()
                isPasswordSymbolValid.postValue(symbolOK)
                if (Constants.DEBUG) Log.e(TAG, "isPasswordSymbolValid: $symbolOK")

                //兩條件皆符合才更新
                if (lengthOK && symbolOK) updateInputValue(inputTextIndex, value)
            }
        }

    }

    private fun updateInputValue(inputTextIndex: Int, value: String) {
        inputCheckItem.updateValue(inputTextIndex, value)
        if (Constants.DEBUG) Log.e(TAG, "inputCheckItem: $inputCheckItem")
        isInputValid.postValue(inputCheckItem.isValid())
    }

    fun resetPassword(password: String) {
        if (inputCheckItem.getValue(0) != inputCheckItem.getValue(1)) {
            isConfirmPasswordValid.postValue(false)
            return
        }

        if (Constants.DEBUG && Constants.API_SUCCESS) {
            isVerifySuccess.postValue(true)
            return
        }

        RetrofitServiceManager.apiService
            .resetPassword(password)
            .subscribe({ response ->
                isVerifySuccess.postValue(response.isSuccess)
            }, {
                Log.e(TAG, "register step2 api failed!")
                isVerifySuccess.postValue(false)
            })
    }
}


