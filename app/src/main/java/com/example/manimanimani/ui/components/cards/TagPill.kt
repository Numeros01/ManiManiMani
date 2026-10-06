package com.example.manimanimani.ui.components.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manimanimani.data.classes.ReasonTag

@Composable
fun TagPill(
    tfr: ReasonTag,
    onDelete: () -> Unit
) {
    ElevatedCard {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 2.dp,
                bottom = 2.dp,
                end = 4.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = tfr.text
            )

            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Tag"
                )
            }
        }
    }
}