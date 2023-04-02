package com.lee.republicservicesassignment.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lee.republicservicesassignment.model.Driver
import androidx.compose.foundation.lazy.items
import com.lee.republicservicesassignment.database.DriverEntity.DriverEntity
import com.lee.republicservicesassignment.ui.driverlist.DriverListViewModel

@Composable
fun SortByLastNameButton(driverDatabaseList: List<DriverEntity>, viewModel: DriverListViewModel) {
    Button(onClick = {
        /*TODO*/
        // sort the list by last name...
        val sortedDriverList = driverDatabaseList.sortedBy {
            it.lastName
        }.map {
            Driver(it.id.toString(), "${it.firstName} ${it.lastName}")
        }.toList()

        viewModel.updateDrivers(sortedDriverList)


    }) {
        Text(text = "Sort by Last Name")
    }
}

@Composable
fun DriverListView(driverList: List<Driver>) {
    val drivers = remember { driverList }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = drivers,
            itemContent = {
                //DriverListItem(driver = it)
            })
    }
}

@Composable
fun DriverListItem(driver: Driver, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onClick.invoke(driver.id.toInt())
            }
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Driver Name: ${driver.name}", style = typography.h5)
                Text(text = "Driver ID: ${driver.id}", style = typography.h6)
            }
        }
    }
}