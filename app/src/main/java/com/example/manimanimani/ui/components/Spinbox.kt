package com.example.manimanimani.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spinbox(
    value: Int,
    increment: () -> Unit,
    decrement: () -> Unit,
    setValue: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = decrement
        ) {
            Text("-")
        }

        TextField(
            value = value.toString(),
            onValueChange = { newText ->
                setValue(newText.toIntOrNull() ?: 0)
            },
            singleLine = true,
            modifier = Modifier.width(100.dp)
        )

        IconButton(
            onClick = increment
        ) {
            Text("+")
        }
    }
}