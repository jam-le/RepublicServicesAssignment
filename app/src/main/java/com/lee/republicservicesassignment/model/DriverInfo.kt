package com.lee.republicservicesassignment.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriverInfo(
    @Json(name="drivers") val drivers: List<Driver>,
    @Json(name="routes") val routes: List<Route>
)