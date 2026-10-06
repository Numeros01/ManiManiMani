package com.example.manimanimani.ui.dialogs.receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
fun EditReceiptMoneyDialog(
    receipt: Receipt,
    vm: ReceiptViewModel,
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int?) -> Unit
) {
    val warn1 = "Changing the money amount may force to change the reason."
    val warn2 = "Current reason has a determined money value. You have to change the reason."

    var amount by remember{mutableStateOf(receipt.amount.toString())}
    var manualReason by remember{mutableStateOf("")}

    // current reason's constAmount
    var currentAmount by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(receipt.reason_id) {
        currentAmount = vm.getReasonById(receipt.reason_id)
            ?.constAmount
    }

    var warningText by remember {mutableStateOf(
        if(currentAmount != null) warn2 else warn1
    )}

    LaunchedEffect(amount) {
        if(amount.isNotEmpty())
            warningText = if(currentAmount != null) warn2 else warn1
        else
            warningText = warn1
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
            else -> "Update Receipt Money"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {onConfirm(amount, manualReason, currentAmount)},
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
                    "Update Receipt Money",
                color = MutedGray
            )
        },
        text = {
            Column {
                TextField(
                    value = amount,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter {
                            it.isDigit() || it == '.'
                        }

                        if (filtered.count { it == '.' } <= 1) {
                            amount = filtered
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    label = { Text("Amount") },
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = MutedGray,
                        focusedTextColor = SoftWhite,
                        unfocusedContainerColor = DarkGray,
                        focusedContainerColor = SoftDark,
                        focusedIndicatorColor = MMMAccent,
                        unfocusedLabelColor = SoftWhite,
                        focusedLabelColor = MMMAccent
                    ),
                    trailingIcon = {
                        if(amount.isNotEmpty()) {
                            IconButton(
                                onClick = { amount = "" }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

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

                Text(
                    text = warningText,
                    color = MutedGray
                )
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