package com.example.manimanimani.ui.components.DataScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.theme.DarkGray
import com.example.manimanimani.ui.theme.MMMAccent
import com.example.manimanimani.ui.theme.MutedGray
import com.example.manimanimani.ui.theme.SoftDark
import com.example.manimanimani.ui.theme.SoftWhite

@Composable
fun DataScreenSearchRow(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSumClick: () -> Unit,
    selectedTab: Int,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .weight(1f),
            placeholder = {
                Text(
                    text = when(selectedTab) {
                        0 -> "Search Receipts"
                        1 -> "Search Reasons"
                        2 -> "Search Tags"
                        else -> "Something's fucked up"
                    }
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MMMAccent
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = onClearQuery
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MutedGray,
                focusedTextColor = SoftWhite,
                unfocusedLeadingIconColor = MutedGray,
                focusedLeadingIconColor = SoftWhite,
                focusedTrailingIconColor = MutedGray,
                focusedContainerColor = SoftDark,
                unfocusedContainerColor = DarkGray,
                unfocusedIndicatorColor = MMMAccent,
                focusedIndicatorColor = SoftWhite,
                focusedLabelColor = MMMAccent,
                unfocusedLabelColor = SoftWhite,
                cursorColor = MutedGray
            ),
            shape = RoundedCornerShape(12.dp)
        )

        FilledIconButton(
            onClick = onSumClick,
            modifier = Modifier.size(56.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MMMAccent,
                contentColor = SoftWhite
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp),
                tint = SoftWhite
            )
        }
    }
}