package com.example.manimanimani.ui.components.DataScreen

import androidx.compose.runtime.Composable
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.ui.dialogManagers.ConfirmationDialogs
import com.example.manimanimani.ui.dialogManagers.DataDialogs.DataScreenReasonDialogs
import com.example.manimanimani.ui.dialogManagers.DataDialogs.DataScreenReceiptDialogs
import com.example.manimanimani.ui.dialogManagers.DataDialogs.DataScreenTagDialogs
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.dialogs.SumDialog
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun DataScreenDialogs(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    scope: CoroutineScope,
    selectedReceipt: Receipt,
    selectedReason: Reason,
    selectedTag: ReasonTag,
    receipts: List<Receipt>,
    tagToReasons: List<TagToReason>,
    onHide: (yes: Boolean) -> Unit
) {
    DataScreenReceiptDialogs(
        vm = vm,
        dm = dm,
        em = em,
        selectedReceipt = selectedReceipt,
        scope = scope
    )

    DataScreenReasonDialogs(
        vm = vm,
        dm = dm,
        em = em,
        selectedReason = selectedReason,
        scope = scope
    )

    DataScreenTagDialogs(
        vm = vm,
        dm = dm,
        em = em,
        selectedTag = selectedTag,
        scope = scope,
        onHide = onHide
    )

    if(dm.isOpen(DialogState.SUM)) {
        SumDialog(
            receipts = receipts,
            vm = vm,
            onReset = {
                dm.closeDialog()
                dm.openDialog(DialogState.CONFIRM_WIPE_RECEIPTS)
            },
            onConfirm = { dm.closeDialog() }
        )
    }

    ConfirmationDialogs(
        vm = vm,
        dm = dm,
        onConfirmWipeReceipts = {
            vm.deleteAllReceipts()
            dm.closeDialog()
        },
        onConfirmDeleteReceipt = {
            vm.deleteReceipt(selectedReceipt)
            dm.closeDialog()
        },
        onConfirmDeleteReason = {
            vm.deleteReason(selectedReason)
            dm.closeDialog()
        },
        onConfirmDeleteTag = {
            vm.deleteTag(selectedTag)
            dm.closeDialog()
        }
    )
}