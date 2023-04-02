package com.lee.republicservicesassignment.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route(
    @Json(name="id") val id: Int,
    @Json(name="type") val type: String,
    @Json(name="name") val name: String
)