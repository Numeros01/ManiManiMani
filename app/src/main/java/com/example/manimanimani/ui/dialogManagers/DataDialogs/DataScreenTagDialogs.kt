package com.example.manimanimani.ui.dialogManagers.DataDialogs

import androidx.compose.runtime.Composable
import com.example.manimanimani.data.classes.ReasonTag
import com.example.manimanimani.data.classes.tagZero
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.components.checkTagDuplicate
import com.example.manimanimani.ui.components.checkTagValid
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.dialogs.tag.AddTagDialog
import com.example.manimanimani.ui.dialogs.tag.EditTagDialog
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DataScreenTagDialogs(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    selectedTag: ReasonTag,
    scope: CoroutineScope,
    onHide: (yes: Boolean) -> Unit
) {
    if (dm.isOpen(DialogState.ADD_TAG))
    {
        AddTagDialog(
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { name ->
                if (name == "show") {
                    onHide(false)
                    dm.closeDialog()
                }
                else if (name == "hide") {
                    onHide(true)
                    dm.closeDialog()
                }
                else {
                    val error = checkTagValid(name)
                    if (error == ErrorState.NONE) {
                        val tag = ReasonTag(text = name)
                        if (checkTagDuplicate(vm, tag)) {
                            scope.launch {
                                em.showError(ErrorState.TAG_DUPLICATE)
                                delay(3000)
                                em.closeError()
                            }
                        } else {
                            vm.addTag(name)
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
            }
        )
    }

    if (dm.isOpen(DialogState.EDIT_TAG) && selectedTag != tagZero)
    {
        EditTagDialog(
            tag = selectedTag,
            em = em,
            onDismiss = { dm.closeDialog() },
            onConfirm = { text: String ->
                val error = checkTagValid(text)
                if (error == ErrorState.NONE) {
                    val tagCopy = selectedTag.copy(text = text)
                    if (checkTagDuplicate(vm, tagCopy)) {
                        scope.launch {
                            em.showError(ErrorState.TAG_DUPLICATE)
                            delay(3000)
                            em.closeError()
                        }
                    } else {
                        vm.updateTag(tagCopy)
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
}