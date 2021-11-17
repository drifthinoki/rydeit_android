package com.rydeit.io

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.api.RetrofitServiceManager
import com.rydeit.io.api.responses.Login
import com.rydeit.io.config.Constants.API_SUCCESS
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User
import com.rydeit.io.ui.InputCheckItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainViewModel():ViewModel() {

    private val TAG = this::class.java.simpleName
    var isLogin: MutableLiveData<Boolean> = UserHelper.isLogin

    fun getUser():User? {
        return UserHelper.getUser()
    }


}
