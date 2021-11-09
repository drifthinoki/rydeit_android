package com.progressingtoday.rydeit.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.progressingtoday.rydeit.config.Constants
import com.progressingtoday.rydeit.helper.UserHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

object RetrofitServiceManager {

    private val TAG = this::class.java.simpleName
    val apiService: ApiService
    val apiServiceWithoutHeaders: ApiService
    private val retrofit: Retrofit
    private val retrofitWithoutHeaders: Retrofit
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            if (Constants.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            }else {
                HttpLoggingInterceptor.Level.NONE
            })
    private val headerInterceptor = object :Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = UserHelper.getToken()
                ?: throw IllegalStateException("Token is not found!")

            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            return chain.proceed(request)
        }

    }
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    private val okHttpClientWithoutHeaders = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofitWithoutHeaders = Retrofit.Builder()
            .baseUrl(Constants.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClientWithoutHeaders)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        apiServiceWithoutHeaders = retrofitWithoutHeaders.create(ApiService::class.java)
        apiService = retrofit.create(ApiService::class.java)
    }
}