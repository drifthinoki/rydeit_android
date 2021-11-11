package com.rydeit.io.helper

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rydeit.io.config.Constants
import com.rydeit.io.model.User
import com.rydeit.io.utils.StorageUtil
import java.lang.IllegalStateException

object UserHelper {

    private val TAG = this::class.java.simpleName

    private var _user: User? = null
    var user: MutableLiveData<User?> = MutableLiveData(null)
    var isLogin: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val user = readPreference()
        user?.let {
            this._user = it
        }

        Log.e(TAG, "user isNull: ${_user == null}, email: ${_user?.email}, isLogin: ${isLogin()}")
        updateLiveData()
    }

    fun setUser(user: User) {
        this._user = user
        savePreference()
    }

    /**
     * 用戶需要完成 3 步驟, 才是真的登入
     * 1.信箱, 密碼登入
     * 2.信箱, 簡訊, Google2FA 驗證
     * 3.設定 Pin 碼
     */
    fun login() {
        _user?.let {
            it.login()
            isLogin.postValue(true)
            savePreference()
        } ?: throw IllegalStateException()
    }

    fun logout() {
        _user?.let {
            it.logout()
            isLogin.postValue(false)
            savePreference()
        } ?: throw IllegalStateException()
    }

    fun isLogin():Boolean {
        return _user != null && _user!!.getToken() != null
    }

    fun getTokenOrTmpToken():String? {
        return _user?.getTokenOrTmpToken()
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