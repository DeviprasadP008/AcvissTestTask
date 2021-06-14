package com.AcvissTechnologies.acvisstesttask.data.network

import com.AcvissTechnologies.acvisstesttask.data.responses.SampleResponse
import com.AcvissTechnologies.acvisstesttask.data.responses.ScanLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ScanApi {

   /* @FormUrlEncoded
    @POST("auth/login")
    fun saveScan(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<List<ScanLiveData>>*/

    @FormUrlEncoded
    @POST("userlogin")
    fun saveScan(
        @Field("contact_no") email: String,
        @Field("password") password: String
    ) : Call<SampleResponse>


}