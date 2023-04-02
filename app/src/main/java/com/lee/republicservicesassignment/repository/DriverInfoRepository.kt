package com.lee.republicservicesassignment.repository

import com.lee.republicservicesassignment.model.DriverInfo
import com.lee.republicservicesassignment.network.DriverInfoApi
import retrofit2.Call

class DriverInfoRepository(
        private val driverInfoApi: DriverInfoApi
    ) {

    fun getDriverInfo(): Call<DriverInfo> = driverInfoApi.retrofitService.getDriversAndRoutes()
}