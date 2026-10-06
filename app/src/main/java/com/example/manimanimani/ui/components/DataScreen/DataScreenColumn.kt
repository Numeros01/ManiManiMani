package com.example.manimanimani.ui.components.DataScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.data.classes.reasonZero
import com.example.manimanimani.data.classes.receiptZero
import com.example.manimanimani.data.classes.tagZero
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.components.cards.ReasonRow
import com.example.manimanimani.ui.components.cards.ReceiptRow
import com.example.manimanimani.ui.components.cards.TagRow
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.viewmodel.ReceiptViewModel

@Composable
fun DataScreenColumn(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    selectedReceipt: Receipt,
    selectedReason: Reason,
    selectedTag: ReasonTag,
    selectedTab: Int,
    receipts: List<Receipt>,
    reasons: List<Reason>,
    tags: List<ReasonTag>,
    onClickReceipt: (Receipt) -> Unit,
    onClickReason: (Reason) -> Unit,
    onClickTag: (ReasonTag) -> Unit,
    modifier: Modifier = Modifier,
    doHide: Boolean
) {
    LazyColumn(
        modifier = modifier
    ) {
        when(selectedTab) {
            0 -> { // Receipts
                items(receipts) { receipt: Receipt ->
                    if(!receipt.isHidden || !doHide) {
                        ReceiptRow(
                            receipt = receipt,
                            onClick = { onClickReceipt(receipt) },
                            onEditReason = { dm.openDialog(DialogState.EDIT_RECEIPT_REASON) },
                            onEditMoney = { dm.openDialog(DialogState.EDIT_RECEIPT_MONEY) },
                            onEditDescription = { dm.openDialog(DialogState.EDIT_RECEIPT_DESC) },
                            onHide = {yes: Boolean -> vm.hideReceipt(receipt, yes)},
                            onDelete = { dm.openDialog(DialogState.CONFIRM_DELETE_RECEIPT) },
                            isSelected = (
                                selectedReceipt != receiptZero && selectedReceipt.id == receipt.id
                            ),
                            doHide = doHide
                        )
                    }
                }
            }

            1 -> { // Reasons
                items(reasons) { reason: Reason ->
                    ReasonRow(
                        reason = reason,
                        onClick = { onClickReason(reason) },
                        onEdit = {dm.openDialog(DialogState.EDIT_REASON)},
                        onEditDesc = {dm.openDialog(DialogState.EDIT_REASON_DESC)},
                        onEditTags = {dm.openDialog(DialogState.EDIT_REASON_TAGS)},
                        onDelete = {dm.openDialog(DialogState.CONFIRM_DELETE_REASON)},
                        isSelected = (
                            selectedReason != reasonZero && selectedReason.id == reason.id
                        )
                    )
                }
            }

            2 -> { // Tags
                items(tags) { tag: ReasonTag ->
                    TagRow(
                        tag = tag,
                        onClick = { onClickTag(tag) },
                        onEdit = {dm.openDialog(DialogState.EDIT_TAG)},
                        onDelete = {dm.openDialog(DialogState.CONFIRM_DELETE_TAG)},
                        isSelected = (
                            selectedTag != tagZero && selectedTag.id == tag.id
                        )
                    )
                }
            }
        }
    }
}