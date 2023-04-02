package com.lee.republicservicesassignment.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Driver(
    @Json(name="id") val id: String,
    @Json(name="name") val name: String
)