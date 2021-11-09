package com.progressingtoday.rydeit.helper

import androidx.lifecycle.MutableLiveData
import com.progressingtoday.rydeit.config.Constants
import com.progressingtoday.rydeit.model.User
import com.progressingtoday.rydeit.utils.StorageUtil

object UserHelper {

    private val TAG = this::class.java.simpleName

    private var _user: User? = null
    var user: MutableLiveData<User?> = MutableLiveData(null)
    var isLogin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val user = readPreference()
        user?.let {
            this._user = it
        }
        updateLiveData()
    }

    fun setUser(user: User) {
        this._user = user
        savePreference()
    }

    fun isLogin():Boolean {
        return _user != null && _user!!.getTokenOrNull() != null
    }

    fun getToken():String? {
        return _user?.getTokenOrNull()
    }

    private fun readPreference():User? {
        return StorageUtil.getObject<User>(Constants.PREF_USER)
    }

    private fun savePreference() {
        StorageUtil.storeObject(Constants.PREF_USER, _user)
    }


    private fun updateLiveData() {
        this.user.value = _user
        this.isLogin.value = isLogin()
    }
}