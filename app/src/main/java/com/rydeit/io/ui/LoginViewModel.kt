package com.rydeit.io

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.LoginResponse
import com.rydeit.io.config.Constants.API_SUCCESS
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User
import com.rydeit.io.ui.InputCheckItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class LoginViewModel(application: Application):AndroidViewModel(application) {
    private val TAG = this::class.java.simpleName
    private val inputCheckItem = InputCheckItem(2)
    var isInputValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoginSuccess: MutableLiveData<Boolean?> = MutableLiveData(null)


    fun updateInputValue(inputTextIndex: Int, value: String) {
        inputCheckItem.updateValue(inputTextIndex, value)
        isInputValid.postValue(inputCheckItem.isValid())
    }

    fun login(email: String, password: String, rememberEmail: Boolean) {
        if (DEBUG && API_SUCCESS) {
            isLoginSuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .login(
                email,
                password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ loginResponse: LoginResponse? ->
                loginResponse?.let { login ->
                    // 記住信箱
                    val user = User(email, login.data.token, rememberEmail)
                    UserHelper.setUser(user)
                    isLoginSuccess.postValue(login.isSuccess)
                }
            }, {
                Log.e(TAG, "login api failed!")
                isLoginSuccess.postValue(false)
            })
    }

}
