package com.rydeit.io.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants

enum class Login2faType {
    EMAIL, SMS, GOOGLE_2FA
}

class Login2faViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    val login2faItem = Login2faItem.empty()
    var isInputTextValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLogin2faSuccess: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun updateLogin2faItem(login2faType: Login2faType, value: String) {
        when(login2faType) {
            Login2faType.EMAIL -> login2faItem.emailVerifyCode = value
            Login2faType.SMS -> login2faItem.smsVerifyCode = value
            Login2faType.GOOGLE_2FA -> login2faItem.google2fa = value
        }
        isInputTextValid.postValue(login2faItem.isValid())
    }

    fun loginVerifyEmail() {
        RetrofitServiceManager.apiService
            .sendEmailVerifyCode()
            .subscribe ()
    }

    fun loginVerifyPhone() {
        RetrofitServiceManager.apiService
            .sendSMSVerifyCode()
            .subscribe ()
    }

    fun login2FA(emailVerifyCode: String, smsVerifyCode: String, google2fa: String) {
        if (Constants.DEBUG && Constants.API_SUCCESS) {
            isLogin2faSuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .login2FA(emailVerifyCode, smsVerifyCode, google2fa)
            .subscribe({ login2fa ->
                isLogin2faSuccess.postValue(login2fa.isSuccess)
            }, {
                Log.e(TAG, "login 2fa api failed!")
                isLogin2faSuccess.postValue(false)
            })
    }

    class Login2faItem(var emailVerifyCode:String, var smsVerifyCode:String, var google2fa: String) {
        companion object {
            fun empty():Login2faItem {
                return Login2faItem("", "", "")
            }
        }

        fun isValid():Boolean {
            return emailVerifyCode.isNotEmpty() && smsVerifyCode.isNotEmpty() && google2fa.isNotEmpty()
        }
    }


}

