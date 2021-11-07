package com.progressingtoday.rydeit.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.progressingtoday.rydeit.config.Constants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

object RetrofitServiceManager {

    private val TAG = this::class.java.simpleName
    val apiService:ApiService
    private val retrofit: Retrofit
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
//        .authenticator(object : Authenticator {
//            override fun authenticate(route: Route?, response: Response): Request? {
//                val token = UserHelper.getMoneyTokenOrNull()
//                    ?: throw IllegalStateException("Money token is empty!")
//
//                Log.e(TAG, "authenticate code: ${response.code}, msg: ${response.message}")
//                return response.request.newBuilder()
//                    .header("X-MONEY", "Bearer $token")
//                    .build()
//            }
//        })
        .build()

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }
}