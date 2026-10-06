package com.example.manimanimani.ui.components.cards

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.ui.theme.*

@Composable
fun ReasonRow(
    reason: Reason,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onEditDesc: () -> Unit,
    onEditTags: () -> Unit,
    onHide: (yes: Boolean) -> Unit,
    onDelete: () -> Unit,
    isSelected: Boolean = false,
    doHide: Boolean
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize()
            .clickable {onClick()},
        colors = CardDefaults.cardColors(
            containerColor =
                if (reason.isHidden) {
                    if (isSelected) MMMAccentDark
                    else DarkerGray
                } else {
                    if(isSelected) MMMAccent
                    else DarkGray
                }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if(isSelected) 8.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reason.name,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )

                Text(
                    text = reason.constAmount.toString(),
                    color = if(isSelected) SoftWhite else MMMAccent
                )

                if(isSelected && reason.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = reason.description,
                        color = MutedGray
                    )
                }

                if(isSelected && reason.isHidden) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Hidden",
                        color = SoftDark
                    )
                }
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
                            text = "Edit Description",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEditDesc()
                        }
                    )

                    DropdownMenuItem(
                        text = {Text(
                            text = "Edit Tags",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEditTags()
                        }
                    )

                    if(!doHide) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = if (reason.isHidden) "Show"
                                            else "Hide",
                                    color = SoftWhite
                                )
                            },
                            onClick = {
                                onHide(!reason.isHidden)
                            }
                        )
                    }

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