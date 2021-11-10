package com.rydeit.io.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.config.Constants

class LoginVerifyViewModel:ViewModel() {

    private val TAG = this::class.java.simpleName
    var isInputTextValid: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoginVerifySuccess: MutableLiveData<Boolean?> = MutableLiveData(null)


    fun loginVerifyEmail() {
        if ( Constants.DEBUG && Constants.API_SUCCESS) {
            isLoginVerifySuccess.postValue(true)
            return
        }
        RetrofitServiceManager.apiService
            .loginEmailVerify()
            .subscribe ()
    }
}