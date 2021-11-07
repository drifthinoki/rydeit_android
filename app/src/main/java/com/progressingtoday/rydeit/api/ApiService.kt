package com.progressingtoday.rydeit.api

import com.google.gson.JsonObject
import com.progressingtoday.rydeit.api.responses.Login
import com.progressingtoday.rydeit.config.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {

    // region 會員
    /**
     * 登入
     */
    @FormUrlEncoded
    @POST(Constants.API_LOGIN)
    fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): Observable<Login>

    // endregion
}