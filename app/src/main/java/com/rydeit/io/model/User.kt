package com.rydeit.io.model

class User(val email:String, var tmpToken:String, val isRememberEmail: Boolean = false) {

    private var token: String? = null

    // 給 header interceptor 使用
    fun getTokenOrTmpToken(): String? {
        return token ?: if (tmpToken.isEmpty()) null else tmpToken
    }

    fun getToken(): String? {
        return token
    }

    fun login() {
        token = tmpToken
    }

    fun logout() {
        token = null
        tmpToken = ""
    }


}