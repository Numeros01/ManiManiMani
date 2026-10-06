package com.example.manimanimani.ui.dialogs.receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun EditReceiptDescriptionDialog(
    receipt: Receipt,
    vm: ReceiptViewModel,
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var description by remember{mutableStateOf(receipt.description)}

    fun isError() : Boolean {
        return em.isError(listOf(
                ErrorState.RECEIPT_INVALID,
                ErrorState.RECEIPT_REASON_OOB,
                ErrorState.RECEIPT_AMOUNT_OOB,
                ErrorState.RECEIPT_AMOUNT_INVALID,
                ErrorState.RECEIPT_DESC_OOB
            ))
    }

    fun errorText(error: ErrorState) : String {
        return when(error) {
            ErrorState.RECEIPT_INVALID -> "Receipt is Invalid!"
            ErrorState.RECEIPT_AMOUNT_INVALID -> "Amount is Invalid!"
            ErrorState.RECEIPT_AMOUNT_OOB -> "Amount is out of bounds!"
            ErrorState.RECEIPT_REASON_OOB -> "Reason text is out of bounds!"
            ErrorState.RECEIPT_DESC_OOB -> "Description is out of bounds!"
            else -> "Update Receipt Description"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {onConfirm(description)},
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
                    "Update Receipt Description",
                color = MutedGray
            )
        },
        text = {
            Column {
                TextField(
                    value = description,
                    onValueChange = {description = it},
                    singleLine = false,
                    maxLines = 5,
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = MutedGray,
                        focusedTextColor = SoftWhite,
                        unfocusedContainerColor = DarkGray,
                        focusedContainerColor = SoftDark,
                        focusedIndicatorColor = MMMAccent
                    )
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