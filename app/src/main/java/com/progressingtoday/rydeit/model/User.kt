package com.progressingtoday.rydeit.model

class User(val email:String, val token:String, val rememberEmail: Boolean) {

    fun getTokenOrNull(): String? {
        return if (token.isEmpty()) null else token
    }


}