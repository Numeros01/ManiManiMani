package com.example.manimanimani.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manimanimani.data.classes.Reason
import com.example.manimanimani.ui.components.ErrorState
import com.example.manimanimani.ui.components.checkReceiptValid
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogs.ErrorManager
import com.example.manimanimani.ui.theme.*
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager
) {
    val receipts by vm.receipts.collectAsState()
    val reasons by vm.reasons.collectAsState()
    val tags by vm.tags.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf<Reason?>(null) }

    var manualReason by remember {mutableStateOf("")}
    var moneyAmount by remember {mutableStateOf("")}
    var description by remember { mutableStateOf("") }

    LaunchedEffect(manualReason) {
        if(manualReason.isNotEmpty()) {
            selectedReason = null
        }
    }

    LaunchedEffect(selectedReason) {
        if(selectedReason != null) {
            manualReason = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepCharcoal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Pick Reason",
                fontSize = 50.sp,
                color = SoftWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                modifier = Modifier.padding(horizontal = 32.dp),
                onExpandedChange = {isDropdownExpanded = !isDropdownExpanded}
            ) {
                TextField(
                    value = selectedReason?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Reason") },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 32.sp,
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
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
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
                    onDismissRequest = {isDropdownExpanded = false},
                    modifier = Modifier
                        .background(color = DarkGray, shape = RoundedCornerShape(16.dp))
                ) {
                    reasons.forEach { reason: Reason ->
                        DropdownMenuItem(
                            text = {
                                Text(reason.name)
                            },
                            onClick = {
                                selectedReason = reason
                                isDropdownExpanded = false
                                if(selectedReason?.constAmount != null)
                                    moneyAmount = selectedReason?.constAmount.toString()
                            },
                            modifier = Modifier
                                .background(DarkGray)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "or",
                fontSize = 30.sp,
                color = MutedGray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = manualReason,
                onValueChange = {manualReason = it},
                placeholder = { Text("Enter Custom Reason") },
                singleLine = true,
                trailingIcon = {
                    if (manualReason.isNotEmpty()) {
                        IconButton(
                            onClick = {manualReason = ""}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MutedGray,
                    focusedTextColor = SoftWhite,
                    focusedContainerColor = SoftDark,
                    unfocusedContainerColor = DarkGray
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Set Amount",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = SoftWhite
            )

            OutlinedTextField(
                value = moneyAmount,
                onValueChange = { newValue ->
                    val filtered = newValue.filter {
                        it.isDigit() || it == '.'
                    }

                    if (filtered.count { it == '.' } <= 1) {
                        moneyAmount = filtered
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                label = {
                    Text("Amount")
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = DarkGray,
                    focusedContainerColor = DarkGray,
                    unfocusedTextColor = MutedGray,
                    focusedTextColor = MutedGray
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    var text = selectedReason?.name
                    if(text == null)
                        text = manualReason

                    var desc = description
                    if(desc.isBlank())
                        desc = selectedReason?.description ?: ""

                    val error = checkReceiptValid(moneyAmount, text, desc)
                    if(error == ErrorState.NONE) {
                        vm.addReceipt(
                            amount = moneyAmount.toInt(),
                            reason = selectedReason,
                            reasonText = text,
                            description = desc
                        )
                        manualReason = ""
                        selectedReason = null
                        moneyAmount = ""
                        description = ""

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Successfully added receipt!"
                            )
                        }

                    } else {
                        scope.launch {
                            em.showError(error)
                            delay(3000)
                            em.closeError()
                        }
                    }
                },
                modifier = Modifier
                    .size(180.dp, 60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MMMAccent
                )

            ) { Text(
                text = "Add Entry",
                fontSize = 24.sp,
                color = SoftWhite
            )}
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = DarkGray,
                contentColor = MMMAccent
            )
        }
    }
}