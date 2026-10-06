package com.example.manimanimani.ui.dialogs.reason

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.theme.DarkGray
import com.example.manimanimani.ui.theme.DeepCharcoal
import com.example.manimanimani.ui.theme.MMMAccent
import com.example.manimanimani.ui.theme.MMMError
import com.example.manimanimani.ui.theme.MutedGray
import com.example.manimanimani.ui.theme.SoftDark
import com.example.manimanimani.ui.theme.SoftWhite

@Composable
fun EditReasonDescriptionDialog(
    reason: Reason,
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var description by remember {mutableStateOf(reason.description)}

    fun isError() : Boolean {
        return em.isError(listOf(
            ErrorState.REASON_INVALID,
            ErrorState.REASON_DUPLICATE,
            ErrorState.REASON_AMOUNT_OOB,
            ErrorState.REASON_NAME_OOB,
            ErrorState.REASON_DESC_OOB,
            ErrorState.REASON_AMOUNT_INVALID
        ))
    }

    fun errorText(error: ErrorState) : String {
        return when(error) {
            ErrorState.REASON_INVALID -> "Reason is Invalid!"
            ErrorState.REASON_DUPLICATE -> "Reason already exists!"
            ErrorState.REASON_AMOUNT_OOB -> "Amount is out of bounds!"
            ErrorState.REASON_NAME_OOB -> "Name is out of bounds!"
            ErrorState.REASON_DESC_OOB -> "Description is out of bounds!"
            ErrorState.REASON_AMOUNT_INVALID -> "Amount is Invalid!"
            else -> "Edit Reason Description"
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
                text = if(isError())
                    errorText(em.getError())
                else
                    "Edit Reason Description",
                color = MutedGray
            )
        },
        text = {
            Column{
                TextField(
                    value = description,
                    onValueChange = {description = it},
                    label = { Text("Description") },
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
                        if(description.isNotEmpty()) {
                            IconButton(
                                onClick = { description = "" }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
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