package com.rydeit.io.config

object Constants {
    var DEBUG = false
    var API_SUCCESS = false

    //API
    //正式站
    //測試站
    const val DOMAIN = "https://api.test.rydeit.io"
    const val API_LOGIN = "/v3/account/login"
    const val API_LOGIN_SEND_EMAIL_VERIFY_CODE = "/v3/account/login/email"
    const val API_LOGIN_SEND_SMS_VERIFY_CODE = "/v3/account/login/sms"
    const val API_LOGIN_2FA = "/v3/account/login/2fa"
    const val API_REGISTER_STEP_1 = "/v3/registration/basic"


    //PREF KEY
    const val PREF_FILE_KEY = " com.rydeit.io.PREFERENCE_FILE_KEY"
    const val PREF_USER = "pref_user"
    const val PREF_PIN = "pref_pin"

    // Rydeit setting
    const val PIN_ERROR_TIMES = 5
}