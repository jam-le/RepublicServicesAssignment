package com.lee.republicservicesassignment.database.route

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RouteDao {
    @Query("SELECT * FROM routeEntity")
    fun getAll(): List<RouteEntity>

    @Query("SELECT * FROM routeEntity WHERE id IN (:routeIds)")
    fun loadAllByIds(routeIds: IntArray): List<RouteEntity>


    @Query("SELECT * FROM routeEntity WHERE id LIKE :routeId LIMIT 1")
    fun findById(routeId: Int): RouteEntity?

    @Query("SELECT * FROM routeEntity WHERE type LIKE :routeType LIMIT 1")
    fun findByType(routeType: String): RouteEntity?

    @Insert
    fun insertAll(vararg route: RouteEntity)

    @Delete
    fun delete(route: RouteEntity)

}