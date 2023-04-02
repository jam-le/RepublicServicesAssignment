package com.lee.republicservicesassignment.database.Driver

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lee.republicservicesassignment.database.DriverEntity.DriverEntity

@Dao
interface DriverDao {
    @Query("SELECT * FROM driverEntity")
    fun getAll(): List<DriverEntity>

    @Query("SELECT * FROM driverEntity WHERE id IN (:driverIds)")
    fun loadAllByIds(driverIds: IntArray): List<DriverEntity>

    @Query("SELECT * FROM driverEntity WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): DriverEntity

    @Insert
    fun insertAll(vararg driver: DriverEntity)

    @Delete
    fun delete(driver: DriverEntity)
}