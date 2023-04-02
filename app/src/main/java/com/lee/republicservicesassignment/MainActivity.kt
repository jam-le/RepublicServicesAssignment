package com.lee.republicservicesassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.lee.republicservicesassignment.database.AppDatabase
import com.lee.republicservicesassignment.model.Driver
import com.lee.republicservicesassignment.ui.DriverListItem
import com.lee.republicservicesassignment.ui.DriverListView
import com.lee.republicservicesassignment.ui.SortByLastNameButton
import com.lee.republicservicesassignment.ui.driverlist.DriverListViewModel
import com.lee.republicservicesassignment.ui.theme.RepublicServicesAssignmentTheme
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lee.republicservicesassignment.ui.RouteScreen

class MainActivity : ComponentActivity() {
    private val viewModel: DriverListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.database =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "app-database"
            )
            .fallbackToDestructiveMigration()
            .build()

        setContent {
            val drivers by viewModel.drivers.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()
            val route by viewModel.displayRoute.collectAsState()

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(viewModel, isLoading, navController = navController, drivers) }
                composable("routescreen") { RouteScreen(route) }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: DriverListViewModel,
    isLoading: Boolean,
    navController: NavController,
    drivers: List<Driver>?
) {
    RepublicServicesAssignmentTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            when {
                isLoading -> LoadingUi()
                else -> Column {
                    Row(
                        Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        SortByLastNameButton(
                            viewModel.driverEntities.value ?: emptyList(),
                            viewModel
                        )
                    }

                    val onDriverClick = { driverId: Int ->
                        viewModel.getDriverRoute(driverId)
                        // navigate to screen with this driver route
                        navController.navigate("routescreen")

                    }
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(
                            items = drivers ?: emptyList(),
                            itemContent = {
                                DriverListItem(driver = it, onClick = onDriverClick)
                            })
                    }
                }
            }

        }
    }
}

@Composable
fun LoadingUi() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Loading...", style = MaterialTheme.typography.h5)
    }
}