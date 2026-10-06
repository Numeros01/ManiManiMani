package com.example.manimanimani.ui.dialogManagers

import androidx.compose.runtime.Composable
import com.example.manimanimani.ui.dialogs.ConfirmDeleteReason
import com.example.manimanimani.ui.dialogs.ConfirmDeleteReceipt
import com.example.manimanimani.ui.dialogs.ConfirmDeleteTag
import com.example.manimanimani.ui.dialogs.ConfirmWipeReceipts
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.viewmodel.ReceiptViewModel

@Composable
fun ConfirmationDialogs(
    vm: ReceiptViewModel,
    dm: DialogManager,
    onConfirmWipeReceipts: () -> Unit,
    onConfirmDeleteReceipt: () -> Unit,
    onConfirmDeleteReason: () -> Unit,
    onConfirmDeleteTag: () -> Unit,
) {
    if(dm.isOpen(DialogState.CONFIRM_WIPE_RECEIPTS)) {
        ConfirmWipeReceipts(
            onDismiss = {dm.closeDialog()},
            onConfirm = onConfirmWipeReceipts
        )
    }

    if(dm.isOpen(DialogState.CONFIRM_DELETE_RECEIPT)) {
        ConfirmDeleteReceipt(
            onDismiss = {dm.closeDialog()},
            onConfirm = onConfirmDeleteReceipt
        )
    }

    if(dm.isOpen(DialogState.CONFIRM_DELETE_REASON)) {
        ConfirmDeleteReason(
            onDismiss = {dm.closeDialog()},
            onConfirm = onConfirmDeleteReason
        )
    }

    if(dm.isOpen(DialogState.CONFIRM_DELETE_TAG)) {
        ConfirmDeleteTag(
            onDismiss = {dm.closeDialog()},
            onConfirm = onConfirmDeleteTag
        )
    }
}