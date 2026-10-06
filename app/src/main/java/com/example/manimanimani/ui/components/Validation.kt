package com.example.manimanimani.ui.components

import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.Receipt
import com.example.manimanimani.viewmodel.ReceiptViewModel

const val MAX_DESCRIPTION_LENGTH = 200
const val MAX_NAME_LENGTH = 30
const val MIN_NAME_LENGTH = 3
const val MAX_AMOUNT = 10000

fun checkReceiptValid(receipt: Receipt) : ErrorState {
    if(receipt.amount > MAX_AMOUNT || receipt.amount < 1)
        return ErrorState.RECEIPT_AMOUNT_OOB

    if(receipt.reasonText.length > MAX_NAME_LENGTH || receipt.reasonText.length < MIN_NAME_LENGTH)
        return ErrorState.RECEIPT_REASON_OOB

    if(receipt.description.length > MAX_DESCRIPTION_LENGTH)
        return ErrorState.RECEIPT_DESC_OOB

    return ErrorState.NONE
}
fun checkReceiptValid(amount: String, reasonText: String, description: String = "") : ErrorState {
    if(amount.toIntOrNull() == null)
        return ErrorState.RECEIPT_AMOUNT_INVALID

    if(amount.toInt() > MAX_AMOUNT || amount.toInt() < 1)
        return ErrorState.RECEIPT_AMOUNT_OOB

    if(reasonText.length > MAX_NAME_LENGTH || reasonText.length < MIN_NAME_LENGTH)
        return ErrorState.RECEIPT_REASON_OOB

    if(description.length > MAX_DESCRIPTION_LENGTH)
        return ErrorState.RECEIPT_DESC_OOB

    return ErrorState.NONE
}

fun checkReasonValid(reason: Reason) : ErrorState {
    if(reason.name.length > MAX_NAME_LENGTH || reason.name.length < MIN_NAME_LENGTH)
        return ErrorState.REASON_NAME_OOB

    if(reason.description.length > MAX_DESCRIPTION_LENGTH)
        return ErrorState.REASON_DESC_OOB

    reason.constAmount?.let {
        if(it > MAX_AMOUNT || it < 1) return ErrorState.REASON_AMOUNT_OOB
    }

    return ErrorState.NONE
}
fun checkReasonValid(name: String, amount: String = "", description: String = "") : ErrorState {
    if(amount.isNotEmpty() && amount.toIntOrNull() == null)
        return ErrorState.REASON_AMOUNT_INVALID

    if(name.length > MAX_NAME_LENGTH || name.length < MIN_NAME_LENGTH)
        return ErrorState.REASON_NAME_OOB

    if(description.length > MAX_DESCRIPTION_LENGTH)
        return ErrorState.REASON_DESC_OOB

    if(amount.isNotEmpty() && (amount.toInt() > MAX_AMOUNT || amount.toInt() < 1))
        return ErrorState.REASON_AMOUNT_OOB

    return ErrorState.NONE
}

fun checkTagValid(tag: ReasonTag) : ErrorState {
    if(tag.text.length > MAX_NAME_LENGTH || tag.text.length < MIN_NAME_LENGTH)
        return ErrorState.TAG_NAME_OOB

    return ErrorState.NONE
}
fun checkTagValid(text: String) : ErrorState {
    if(text.length > MAX_NAME_LENGTH || text.length < MIN_NAME_LENGTH)
        return ErrorState.TAG_NAME_OOB

    return ErrorState.NONE
}

fun checkReasonDuplicate(vm: ReceiptViewModel, reason: Reason) : Boolean {
    return vm.checkReasonExist(reason)
}

fun checkTagDuplicate(vm: ReceiptViewModel, tag: ReasonTag) : Boolean {
    return vm.checkTagExist(tag)
}