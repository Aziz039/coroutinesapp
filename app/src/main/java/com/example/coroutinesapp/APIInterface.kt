package com.example.coroutinesapp

import retrofit2.http.GET

interface APIInterface {
    @GET("/advice")
    fun doGetListResources(): retrofit2.Call<adviceDetails?>?
}