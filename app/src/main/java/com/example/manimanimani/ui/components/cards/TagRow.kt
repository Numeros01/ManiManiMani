package com.example.manimanimani.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.ui.theme.*

@Composable
fun TagRow(
    tag: ReasonTag,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    isSelected: Boolean = false
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {onClick()},
        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected)
                    MMMAccent
                else
                    DarkGray
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = '#' + tag.text,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            }

            Box {
                IconButton(
                    onClick = {
                        isMenuExpanded = true
                        onClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = {isMenuExpanded = false},
                    shape = RoundedCornerShape(12.dp),
                    containerColor = SoftDark
                ) {
                    DropdownMenuItem(
                        text = {Text(
                            text = "Edit",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEdit()
                        }
                    )

                    DropdownMenuItem(
                        text = {Text(
                            text = "Delete",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}