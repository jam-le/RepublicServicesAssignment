package com.lee.republicservicesassignment.ui.driverlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lee.republicservicesassignment.database.AppDatabase
import com.lee.republicservicesassignment.database.DriverEntity.DriverEntity
import com.lee.republicservicesassignment.database.route.RouteEntity
import com.lee.republicservicesassignment.model.Driver
import com.lee.republicservicesassignment.model.DriverInfo
import com.lee.republicservicesassignment.model.Route
import com.lee.republicservicesassignment.network.DriverInfoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class DriverListViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<Response<DriverInfo>>()

    // The external immutable LiveData for the request status
    val status: LiveData<Response<DriverInfo>> = _status

    private val _drivers: MutableStateFlow<List<Driver>?> = MutableStateFlow(null)
    val drivers: StateFlow<List<Driver>?> = _drivers

    private val _driverEntities: MutableStateFlow<List<DriverEntity>?> = MutableStateFlow(null)
    val driverEntities: StateFlow<List<DriverEntity>?> = _driverEntities

    private val _routes = MutableLiveData<List<Route>>()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _displayRoute: MutableStateFlow<Route?> = MutableStateFlow(null)
    val displayRoute: StateFlow<Route?> = _displayRoute

    lateinit var database: AppDatabase

    companion object {
        const val TAG = "DriverListViewModel"
    }

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDriverInfo()
    }

    private fun getDriverInfo() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = true
                val response = DriverInfoApi.retrofitService.getDriversAndRoutes()
                    .execute() // driverInfoRepository.getDriverInfo().execute()
                val drivers: List<Driver>? = response.body()?.drivers
                val routes: List<Route>? = response.body()?.routes
                _status.postValue(response)
                _drivers.value = drivers
                _routes.postValue(routes)

                // store drivers into database
                try {
                    val driverEntityList = ArrayList<DriverEntity>()
                    drivers?.onEach { driver ->
                        val firstLastName = driver.name.split(" ")
                        driverEntityList.add(
                            DriverEntity(
                                driver.id.toInt(),
                                firstLastName.first(),
                                firstLastName.last()
                            )
                        )
                    }
                    database.driverDao().insertAll(*driverEntityList.toTypedArray())
                    _driverEntities.value = driverEntityList
                } catch (e: Exception) {
                    // Need a function to insert drivers only if unique
                    _driverEntities.value = database.driverDao().getAll()
                }

                try {
                    val routeEntityList = ArrayList<RouteEntity>()
                    routes?.onEach { route ->
                        routeEntityList.add(
                            RouteEntity(route.id, route.type, route.name)
                        )
                    }
                    database.routeDao().insertAll(*routeEntityList.toTypedArray())
                } catch (e: Exception) {
                    // Error handling for wrong
                    Log.e(TAG, "Error adding routes to database")
                }

                _isLoading.value = false
            }
        } catch (e: Exception) {
            // Handle error
        }
    }

    fun updateDrivers(updatedDriverList: List<Driver>) {
        _drivers.update {
            updatedDriverList
        }
    }

    fun getDriverRoute(driverId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val routesMatchingDriverId: RouteEntity? = database.routeDao().findById(driverId)
            routesMatchingDriverId?.apply {
                _displayRoute.value = (Route(this.id, this.type ?: "", this.name ?: ""))
            } ?: run {
                when {
                    (driverId % 2 == 0) -> {
                        val firstRRoute = _routes.value?.first { route ->
                            route.type == "R"
                        }
                        firstRRoute?.apply {
                            _displayRoute.value = this
                        }
                    }
                    (driverId % 5 == 0) -> {
                        val secondCRoute = _routes.value?.filter { it.type == "C" }?.get(1)
                        secondCRoute?.apply {
                            _displayRoute.value = this
                        }
                    }
                    else -> {
                        val lastIRoute = _routes.value?.last { it.type == "I" }
                        lastIRoute?.apply {
                            _displayRoute.value = this
                        }
                    }
                }
            }
        }
    }
}