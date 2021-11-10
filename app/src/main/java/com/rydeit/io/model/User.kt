package com.rydeit.io.model

class User(val email:String, val token:String, val isRememberEmail: Boolean) {

    fun getTokenOrNull(): String? {
        return if (token.isEmpty()) null else token
    }


}