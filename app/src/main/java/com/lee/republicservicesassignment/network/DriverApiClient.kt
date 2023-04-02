package com.lee.republicservicesassignment.network

import com.lee.republicservicesassignment.model.DriverInfo
import retrofit2.Call
import retrofit2.http.GET

interface DriverApiClient {
    companion object {
        const val BASE_URL = "https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io"
    }

    @GET("data") fun getDriversAndRoutes(): Call<DriverInfo>
}