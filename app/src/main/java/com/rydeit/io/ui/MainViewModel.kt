package com.rydeit.io

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.model.User

class MainViewModel():ViewModel() {

    private val TAG = this::class.java.simpleName
    var isLogin: MutableLiveData<Boolean> = UserHelper.isLogin

    fun getUser():User? {
        return UserHelper.getUser()
    }


}
