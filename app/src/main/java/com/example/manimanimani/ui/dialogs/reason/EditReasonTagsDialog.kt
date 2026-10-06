package com.example.manimanimani.ui.dialogs.reason

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.components.cards.TagPill
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
fun EditReasonTagsDialog(
    reason: Reason,
    vm: ReceiptViewModel,
    em: ErrorManager,
    onDismiss: () -> Unit,
    onConfirm: (ReasonTag?) -> Unit
) {
    val tags by vm.tags.collectAsState()
    val tagToReasons by vm.getTTRByReasonId(reason.id)
        .collectAsState(initial = emptyList())

    val tagsForReason by vm.getTagsForReason(reason.id)
        .collectAsState(initial = emptyList())

    var selectedTag by remember {mutableStateOf<ReasonTag?>(null)}

    var isDropdownExpanded by remember {mutableStateOf(false)}

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
            else -> "Edit Reason Tags"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(selectedTag)
                    selectedTag = null
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )
            ) {
                Text(
                    "Add",
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
                    "Exit",
                    color = SoftWhite
                )
            }
        },
        title = {
            Text(
                text = if(isError())
                    errorText(em.getError())
                else
                    "Edit Reason Tags",
                color = MutedGray
            )
        },
        text = {
            Column {
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tagsForReason.forEach { tfr ->
                        TagPill(
                            tfr = tfr,
                            onDelete = {vm.deleteTagToReason(
                                reason.id,
                                tfr.id
                            )}
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                ) {
                    TextField(
                        value = selectedTag?.text ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tag") },
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
                        tags.filter { tag ->
                            tag !in tagsForReason
                        }.forEach { tag: ReasonTag ->
                            DropdownMenuItem(
                                text = {
                                    Text(tag.text)
                                },
                                onClick = {
                                    selectedTag = tag
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
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