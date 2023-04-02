package com.lee.republicservicesassignment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.lee.republicservicesassignment.model.Route

@Composable
fun RouteScreen(route: Route?) {
    route ?: RouteErrorView(errorMessage = "Error: Could not find requested route.")

    if (route != null) {
        Column {
            Text("Route Id: ${route.id}", style = MaterialTheme.typography.h5)
            Text("Route Type: ${route.type}", style = MaterialTheme.typography.h5)
            Text("Route Name: ${route.name}", style = MaterialTheme.typography.h5)
        }

    }
}

@Composable
fun RouteErrorView(errorMessage: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = errorMessage, style = MaterialTheme.typography.h5)
    }
}