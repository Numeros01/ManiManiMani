package com.example.manimanimani.ui.components.DataScreen

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.theme.MMMAccent
import com.example.manimanimani.ui.theme.SoftWhite

@Composable
fun DataScreenAddButton(
    dm: DialogManager,
    navController: NavController,
    selectedTab: Int,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = {
            when(selectedTab) {
                0 -> {navController.navigate("main") {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                }}
                1 -> {dm.openDialog(DialogState.ADD_REASON)}
                2 -> {dm.openDialog(DialogState.ADD_TAG)}
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        containerColor = MMMAccent,
        contentColor = SoftWhite
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            modifier = Modifier.size(32.dp)
        )
    }
}