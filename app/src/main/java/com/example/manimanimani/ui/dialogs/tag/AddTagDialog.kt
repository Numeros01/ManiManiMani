package com.example.manimanimani.ui.dialogs.tag

import androidx.compose.foundation.layout.Column
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
fun AddTagDialog(
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember {mutableStateOf("")}

    fun isError() : Boolean {
        return em.isError(listOf(
            ErrorState.TAG_INVALID,
            ErrorState.TAG_DUPLICATE,
            ErrorState.TAG_NAME_OOB
        ))
    }

    fun errorText(error: ErrorState) : String {
        return when(error) {
            ErrorState.TAG_INVALID -> "Tag is Invalid!"
            ErrorState.TAG_DUPLICATE -> "Tag already exists!"
            ErrorState.TAG_NAME_OOB -> "Name is out of bounds!"
            else -> "Add Tag"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {onConfirm(text)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    text = "Add",
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
                    text = "Cancel",
                    color = SoftWhite
                )
            }
        },
        title = {
            Text(
                if(isError())
                    errorText(em.getError())
                else
                    "Add Tag",
                color = MutedGray
            )
        },
        text = {
            Column{
                TextField(
                    value = text,
                    onValueChange = {text = it},
                    label = {
                        Text("Tag text")
                    },
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
                        if(text.isNotEmpty()) {
                            IconButton(
                                onClick = { text = "" }
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