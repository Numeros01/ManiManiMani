package com.example.manimanimani.ui.dialogs.receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.theme.DarkGray
import com.example.manimanimani.ui.theme.DeepCharcoal
import com.example.manimanimani.ui.theme.MMMAccent
import com.example.manimanimani.ui.theme.MMMError
import com.example.manimanimani.ui.theme.MutedGray
import com.example.manimanimani.ui.theme.SoftDark
import com.example.manimanimani.ui.theme.SoftWhite
import com.example.manimanimani.viewmodel.ReceiptViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReceiptReasonDialog(
    receipt: Receipt,
    vm: ReceiptViewModel,
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (Reason?, String) -> Unit
) {
    val reasons by vm.reasons.collectAsState()

    var selectedReason by remember { mutableStateOf<Reason?>(null)}
    var manualReason by remember{mutableStateOf("")}

    var isDropdownExpanded by remember{mutableStateOf(false)}

    var warningText by remember{mutableStateOf("Some Reasons may change the money amount.")}

    LaunchedEffect(selectedReason) {
        if(selectedReason != null)
            manualReason = ""
    }

    LaunchedEffect(manualReason) {
        if(manualReason.isNotEmpty()) {
            selectedReason = null
            warningText = "Some Reasons may change the money amount."
        }
    }

    LaunchedEffect(receipt.reason_id) {
        selectedReason = vm.getReasonById(receipt.reason_id)
    }

    fun isError() : Boolean {
        if(em.isError(listOf(
                ErrorState.RECEIPT_INVALID,
                ErrorState.RECEIPT_REASON_OOB,
                ErrorState.RECEIPT_AMOUNT_OOB,
                ErrorState.RECEIPT_AMOUNT_INVALID,
                ErrorState.RECEIPT_DESC_OOB
        ))) return true

        return false
    }

    fun errorText(error: ErrorState) : String {
        return when(error) {
            ErrorState.RECEIPT_INVALID -> "Receipt is Invalid!"
            ErrorState.RECEIPT_AMOUNT_INVALID -> "Amount is Invalid!"
            ErrorState.RECEIPT_AMOUNT_OOB -> "Amount is out of bounds!"
            ErrorState.RECEIPT_REASON_OOB -> "Reason text is out of bounds!"
            ErrorState.RECEIPT_DESC_OOB -> "Description is out of bounds!"
            else -> "Update Receipt Reason"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {onConfirm(selectedReason, manualReason)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Save",
                    color = SoftWhite
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Cancel",
                    color = SoftWhite
                )
            }
        },
        title = {
            Text(
                if (isError())
                    errorText(em.getError())
                else
                    "Update Receipt Reason",
                color = MutedGray
            )
        },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = {isDropdownExpanded = !isDropdownExpanded},
                ) {
                    TextField(
                        value = selectedReason?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Reason") },
                        textStyle = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.Bold,
                            color = SoftWhite
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isDropdownExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = DarkGray,
                            focusedContainerColor = SoftDark,
                            unfocusedTextColor = MutedGray,
                            focusedTextColor = SoftWhite,
                            focusedTrailingIconColor = MMMAccent,
                            focusedLabelColor = MMMAccent
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = {
                            isDropdownExpanded = false
                        }
                    ) {
                        reasons.forEach { reason: Reason ->
                            DropdownMenuItem(
                                text = {
                                    Text(reason.name)
                                },
                                onClick = {
                                    selectedReason = reason
                                    isDropdownExpanded = false
                                    if(selectedReason?.constAmount != null) {
                                        warningText = "This Reason will change the money amount!"
                                    }
                                    else {
                                        warningText = "Some Reasons may change the money amount."
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = manualReason,
                    onValueChange = {manualReason = it},
                    label = { Text("Custom Reason") },
                    singleLine = true,
                    trailingIcon = {
                        if (manualReason.isNotEmpty()) {
                            IconButton(
                                onClick = {manualReason = ""}
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
                        focusedContainerColor = SoftDark,
                        unfocusedContainerColor = DarkGray
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = warningText)
            }
        },
        containerColor = (
            if (isError())
                MMMError
            else
                DeepCharcoal
        )
    )
}