package com.progressingtoday.rydeit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.progressingtoday.rydeit.api.RetrofitServiceManager
import com.progressingtoday.rydeit.api.responses.Login
import com.progressingtoday.rydeit.config.Constants.DEBUG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

enum class LoginAccountItemType{
    EMAIL, PASSWORD
}

class LoginAccountViewModel(application: Application):AndroidViewModel(application) {
    private val TAG = this::class.java.simpleName
    private val loginAccountItem = LoginAccountItem.empty()
    var isInputTextValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoginSuccess: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun updateLoginAccountItem(loginAccountItemType: LoginAccountItemType, value:String) {
        when(loginAccountItemType) {
            LoginAccountItemType.EMAIL -> loginAccountItem.email = value
            LoginAccountItemType.PASSWORD -> loginAccountItem.password = value
        }
        isInputTextValid.postValue(loginAccountItem.isValid())
    }

    fun login() {
        RetrofitServiceManager.apiService
            .login(
                loginAccountItem.email,
                loginAccountItem.password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { loginResponse: Login? ->
                if (DEBUG) Log.e(TAG, "login API state: success")
                if (DEBUG) Log.e(TAG, "login API content: ${loginResponse.toString()}")

                loginResponse?.let { login ->
                    if (DEBUG) Log.e(TAG, "login API isSuccess: ${login.isSuccess}")
                    isLoginSuccess.postValue(login.isSuccess)
                }

            }, {throwable: Throwable ->
                if (DEBUG) Log.e(TAG, "login API state: fail")
                if (DEBUG) Log.e(TAG, "login API content : $throwable")
            })
    }
}

class LoginAccountItem(var email:String, var password:String) {
    companion object {
        fun empty():LoginAccountItem {
            return LoginAccountItem("", "")
        }
    }

    fun isValid():Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

}
