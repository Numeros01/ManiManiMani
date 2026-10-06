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
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReceiptRow(
    receipt: Receipt,
    onClick: () -> Unit,
    onEditReason: () -> Unit,
    onEditMoney: () -> Unit,
    onEditDescription: () -> Unit,
    onHide: (yes: Boolean) -> Unit,
    onDelete: () -> Unit,
    isSelected: Boolean = false,
    doHide: Boolean
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    val doShowYear = false
    val doShowMonth = true
    val doShowDay = false // name of day in week, ex. Friday
    val doShowHour = true
    val doShowMinute = true // only if doShowHour
    val doShowSecond = false // only if doShowHour and Minute
    val monthDisplay = 2 // 1 - number, 2 - short, 3 - full name
    val isHourFirst = false

    fun formatTimestamp(timestamp: Long): String {
        val formatter = SimpleDateFormat(
            createDatePattern(
                doShowYear = doShowYear,
                doShowMonth = doShowMonth,
                doShowDay = doShowDay,
                doShowHour = doShowHour,
                doShowMinute = doShowMinute,
                doShowSecond = doShowSecond,
                monthDisplay = monthDisplay,
                isHourFirst = isHourFirst
            ),
            Locale.getDefault()
        )
        return formatter.format(Date(timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize()
            .clickable {onClick()},
        colors = CardDefaults.cardColors(
            containerColor =
                if (receipt.isHidden) {
                    if (isSelected) MMMAccentDark
                    else DarkerGray
                } else {
                    if (isSelected) MMMAccent
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
                    receipt.reasonText,
                    fontWeight = FontWeight.Bold,
                    color = if(receipt.isHidden) MutedGray else SoftWhite
                )

                Text( // amount text
                    text = receipt.amount.toString(),
                    color = if(isSelected) MutedGray else MMMAccent
                )

                if(isSelected && receipt.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = receipt.description,
                        color = MutedGray
                    )
                }

                if(isSelected && receipt.creation_time != 0L) {
                    Text(
                        text = "created " +
                                formatTimestamp(receipt.creation_time),
                        color = SoftDark
                    )
                }
            }

                if(receipt.isHidden && isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "hidden",
                        color = SoftDark
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
                            text = "Edit Reason",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEditReason()
                        }
                    )
                    DropdownMenuItem(
                        text = {Text(
                            text = "Edit Money",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEditMoney()
                        }
                    )
                    DropdownMenuItem(
                        text = {Text(
                            text = "Edit Description",
                            color = SoftWhite
                        )},
                        onClick = {
                            isMenuExpanded = false
                            onEditDescription()
                        }
                    )
                    // Hide button
                    if (!doHide) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text =
                                        if (receipt.isHidden) "Show"
                                        else "Hide",
                                    color = SoftWhite
                                )
                            },
                            onClick = {
                                isMenuExpanded = false
                                onHide(!receipt.isHidden)
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