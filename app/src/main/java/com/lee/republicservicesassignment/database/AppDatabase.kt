package com.lee.republicservicesassignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lee.republicservicesassignment.database.Driver.DriverDao
import com.lee.republicservicesassignment.database.DriverEntity.DriverEntity
import com.lee.republicservicesassignment.database.route.RouteDao
import com.lee.republicservicesassignment.database.route.RouteEntity

@Database(entities = [DriverEntity::class, RouteEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao
}