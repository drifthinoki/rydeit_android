package com.rydeit.io

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.Login
import com.rydeit.io.config.Constants.API_SUCCESS
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User
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

    fun login(rememberEmail: Boolean) {
        if ( DEBUG && API_SUCCESS) {
            isLoginSuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiServiceWithoutHeaders
            .login(
                loginAccountItem.email,
                loginAccountItem.password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loginResponse: Login? ->
                loginResponse?.let { login ->
                    // 記住信箱
                    val user = User(loginAccountItem.email, login.data.token, rememberEmail)
                    UserHelper.setUser(user)
                    isLoginSuccess.postValue(login.isSuccess)
                }
            }
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
