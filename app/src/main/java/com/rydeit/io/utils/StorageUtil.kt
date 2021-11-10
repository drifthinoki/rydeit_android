package com.rydeit.io.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.rydeit.io.config.Constants

object StorageUtil {

    lateinit var sharePreferences: SharedPreferences

    fun with(application: Application) {
        sharePreferences = application.getSharedPreferences(Constants.PREF_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun <T> storeObject(key: String, obj: T) {

        val jsonString = GsonBuilder()
            .create()
            .toJson(obj)

        sharePreferences.edit()
            .putString(key, jsonString)
            .apply()
    }

    inline fun <reified T> getObject(key: String): T? {
        val value = sharePreferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }
}