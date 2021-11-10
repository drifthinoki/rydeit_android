package com.rydeit.io.config

object Constants {
    var DEBUG = true
    var API_SUCCESS = false

    //API
    //正式站
    //測試站
    const val DOMAIN = "https://api.test.rydeit.io"
    const val API_LOGIN = "/v3/account/login"
    const val API_LOGIN_SEND_EMAIL_VERIFY_CODE = "/v3/account/login/email"
    const val API_LOGIN_SEND_SMS_VERIFY_CODE = "/v3/account/login/sms"
    const val API_LOGIN_2FA = "/v3/account/login/2fa"

    //PREF KEY
    const val PREF_FILE_KEY = " com.rydeit.io.PREFERENCE_FILE_KEY"
    const val PREF_USER = "pref_user"
}