package com.progressingtoday.rydeit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

enum class LoginAccountItemType{
    EMAIL, PASSWORD
}

class LoginAccountViewModel(application: Application):AndroidViewModel(application) {

    private val loginAccountItem = LoginAccountItem.empty()
    var isInputTextValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun updateLoginAccountItem(loginAccountItemType: LoginAccountItemType, value:String) {
        when(loginAccountItemType) {
            LoginAccountItemType.EMAIL -> loginAccountItem.email = value
            LoginAccountItemType.PASSWORD -> loginAccountItem.password = value
        }
        isInputTextValid.postValue(loginAccountItem.isValid())
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
