package com.lee.republicservicesassignment.database.route

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouteEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "name") val name: String?
)