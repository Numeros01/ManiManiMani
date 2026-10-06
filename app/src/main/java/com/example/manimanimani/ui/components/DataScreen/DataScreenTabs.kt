package com.example.manimanimani.ui.components.DataScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.manimanimani.ui.theme.*

@Composable
fun DataScreenTabs(
    getTab: () -> Int,
    setTab: (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = getTab(),
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = DarkGray
    ) {
        Tab(
            selected = (getTab() == 0),
            onClick = { setTab(0) },
            text = {Text("Receipts")},
            selectedContentColor = MMMAccent,
            unselectedContentColor = MutedGray
        )

        Tab(
            selected = (getTab() == 1),
            onClick = { setTab(1) },
            text = {Text("Reasons")},
            selectedContentColor = MMMAccent,
            unselectedContentColor = MutedGray
        )

        Tab(
            selected = (getTab() == 2),
            onClick = { setTab(2) },
            text = {Text(text = "Tags")},
            selectedContentColor = MMMAccent,
            unselectedContentColor = MutedGray
        )
    }
}