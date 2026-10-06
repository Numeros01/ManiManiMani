package com.example.manimanimani.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.screens.data.DataScreen
import com.example.manimanimani.ui.screens.main.MainScreen
import com.example.manimanimani.ui.theme.*
import com.example.manimanimani.viewmodel.ReceiptViewModel

@Composable
fun NavGraph(
    MMMViewModel: ReceiptViewModel,
    MMMDialogManager: DialogManager,
    MMMErrorManager: ErrorManager
)
{
    val navController = rememberNavController()

    val currentTab =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    var doHideHidden by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkGray
            ) {
                NavigationBarItem(
                    selected = (currentTab == "main"),
                    onClick = {
                        navController.navigate("main")  {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    label = {
                        Text("Main")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Main"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = MutedGray,
                        selectedTextColor = SoftWhite,
                        unselectedIconColor = MutedGray,
                        selectedIconColor = SoftWhite,
                        indicatorColor = MMMAccent
                    )
                )

                // Data Tab
                NavigationBarItem(
                    selected = (currentTab == "data"),
                    onClick = {
                        navController.navigate("data")  {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    label = {
                        Text("Data")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Data"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = MutedGray,
                        selectedTextColor = SoftWhite,
                        unselectedIconColor = MutedGray,
                        selectedIconColor = SoftWhite,
                        indicatorColor = MMMAccent
                    )
                )
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(padding)
        ) {
            composable("main") {
                MainScreen(
                    vm = MMMViewModel,
                    dm = MMMDialogManager,
                    em = MMMErrorManager,
                    doHide = doHideHidden
                )
            }
            composable("data") {
                DataScreen(
                    vm = MMMViewModel,
                    dm = MMMDialogManager,
                    em = MMMErrorManager,
                    navController,
                    doHide = doHideHidden,
                    setHide = {yes -> doHideHidden = yes}
                )
            }
        }
    }
}