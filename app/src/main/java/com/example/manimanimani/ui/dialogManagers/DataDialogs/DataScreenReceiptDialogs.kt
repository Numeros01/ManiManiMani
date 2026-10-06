package com.example.manimanimani.ui.dialogManagers.DataDialogs

import androidx.compose.runtime.Composable
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.classes.receiptZero
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.components.checkReceiptValid
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.dialogs.receipt.EditReceiptDescriptionDialog
import com.example.manimanimani.ui.dialogs.receipt.EditReceiptMoneyDialog
import com.example.manimanimani.ui.dialogs.receipt.EditReceiptReasonDialog
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DataScreenReceiptDialogs(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    selectedReceipt: Receipt,
    scope: CoroutineScope
) {
    if (dm.isOpen(DialogState.EDIT_RECEIPT_REASON) && selectedReceipt != receiptZero)
    {
        EditReceiptReasonDialog(
            receipt = selectedReceipt,
            vm = vm,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { reason: Reason?, reasonText: String ->
                var text = reason?.name
                if(text == null)
                    text = reasonText

                var amount = selectedReceipt.amount
                val tempAmount = reason?.constAmount
                if(tempAmount != null) {
                    amount = tempAmount
                }

                var desc = selectedReceipt.description
                if(reason != null && reason.description.isNotEmpty() && desc.isBlank()) {
                    desc = reason.description
                }

                val error = checkReceiptValid(amount.toString(), text, desc)
                if(error == ErrorState.NONE) {
                    vm.updateReceipt(selectedReceipt.copy(
                        amount = amount,
                        reason_id = reason?.id,
                        reasonText = text,
                        description = desc
                    ))
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

    if (dm.isOpen(DialogState.EDIT_RECEIPT_MONEY) && selectedReceipt != receiptZero)
    {
        EditReceiptMoneyDialog(
            receipt = selectedReceipt,
            vm = vm,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { amount: String, reasonText: String, currentAmount: Int? ->
                if(currentAmount != null && reasonText.isEmpty()) {
                    scope.launch {
                        em.showError(ErrorState.RECEIPT_AMOUNT_INVALID)
                        delay(3000)
                        em.closeError()
                    }
                } else {
                    var text = selectedReceipt.reasonText
                    if(currentAmount != null) {
                        text = reasonText
                    }

                    val error = checkReceiptValid(amount, text)
                    if(error == ErrorState.NONE) {
                        vm.updateReceipt(selectedReceipt.copy(
                            amount = amount.toInt(),
                            reasonText = text
                        ))
                        dm.closeDialog()
                    } else {
                        scope.launch {
                            em.showError(error)
                            delay(3000)
                            em.closeError()
                        }
                    }
                }
            }
        )
    }

    if (dm.isOpen(DialogState.EDIT_RECEIPT_DESC) && selectedReceipt != receiptZero)
    {
        EditReceiptDescriptionDialog(
            receipt = selectedReceipt,
            vm = vm,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { description: String ->
                val error = checkReceiptValid(selectedReceipt.copy(description = description))
                if (error == ErrorState.NONE) {
                    vm.updateReceipt(selectedReceipt.copy(description = description))
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
}