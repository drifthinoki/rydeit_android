package com.progressingtoday.rydeit.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.progressingtoday.rydeit.api.RetrofitServiceManager
import com.progressingtoday.rydeit.api.responses.Login
import com.progressingtoday.rydeit.config.Constants
import com.progressingtoday.rydeit.helper.UserHelper
import com.progressingtoday.rydeit.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

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