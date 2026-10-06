package com.example.manimanimani.ui.dialogManagers.DataDialogs

import androidx.compose.runtime.Composable
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.TagToReason
import com.example.manimanimani.data.classes.reasonZero
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.components.checkReasonDuplicate
import com.example.manimanimani.ui.components.checkReasonValid
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.dialogs.reason.AddReasonDialog
import com.example.manimanimani.ui.dialogs.reason.EditReasonDescriptionDialog
import com.example.manimanimani.ui.dialogs.reason.EditReasonDialog
import com.example.manimanimani.ui.dialogs.reason.EditReasonTagsDialog
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DataScreenReasonDialogs(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    selectedReason: Reason,
    scope: CoroutineScope
) {
    if (dm.isOpen(DialogState.ADD_REASON))
    {
        AddReasonDialog(
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { name: String, description: String, amount: String ->
                val error = checkReasonValid(name, amount, description)
                if(error == ErrorState.NONE) {
                    val reason = Reason(
                        name = name,
                        description = description,
                        constAmount = amount.toIntOrNull()
                    )
                    if (checkReasonDuplicate(vm, reason)) {
                        scope.launch {
                            em.showError(ErrorState.REASON_DUPLICATE)
                            delay(3000)
                            em.closeError()
                        }
                    } else {
                        vm.addReason(reason)
                        dm.closeDialog()
                    }
                } else {
                    scope.launch {
                        em.showError(error)
                        delay(3000)
                        em.closeError()
                    }
                }
            }
        )
    }

    if (dm.isOpen(DialogState.EDIT_REASON) && selectedReason != reasonZero)
    {
        EditReasonDialog(
            reason = selectedReason,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { name: String, amount: String ->
                val error = checkReasonValid(name, amount)
                if(error == ErrorState.NONE) {
                    val reasonCopy = selectedReason.copy(
                        name = name,
                        constAmount = amount.toIntOrNull()
                    )
                    if (checkReasonDuplicate(vm, reasonCopy)) {
                        scope.launch {
                            em.showError(ErrorState.REASON_DUPLICATE)
                            delay(3000)
                            em.closeError()
                        }
                    } else {
                        vm.updateReason(reasonCopy)
                        dm.closeDialog()
                    }
                }else {
                    scope.launch {
                        em.showError(error)
                        delay(3000)
                        em.closeError()
                    }
                }
            }
        )
    }

    if (dm.isOpen(DialogState.EDIT_REASON_DESC) && selectedReason != reasonZero)
    {
        EditReasonDescriptionDialog(
            reason = selectedReason,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { description: String ->
                val reasonCopy = selectedReason.copy(description = description)
                val error = checkReasonValid(reasonCopy)
                if (error == ErrorState.NONE) {
                    vm.updateReason(reasonCopy)
                    dm.closeDialog()
                } else {
                    scope.launch {
                        em.showError(error)
                        delay(3000)
                        em.closeError()
                    }
                }
            }
        )
    }

    if(dm.isOpen(DialogState.EDIT_REASON_TAGS) && selectedReason != reasonZero) {
        EditReasonTagsDialog(
            reason = selectedReason,
            vm = vm,
            em = em,
            onDismiss = {dm.closeDialog()},
            onConfirm = { tag: ReasonTag? ->
                if(tag != null)
                    vm.addTagToReason(
                        selectedReason.id,
                        tag.id
                    )
            }
        )
    }
}