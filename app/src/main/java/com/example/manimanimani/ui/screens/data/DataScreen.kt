package com.example.manimanimani.ui.screens.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.manimanimani.data.classes.*
import com.example.manimanimani.ui.components.DataScreen.DataScreenDialogs
import com.example.manimanimani.ui.components.DataScreen.DataScreenAddButton
import com.example.manimanimani.ui.components.DataScreen.DataScreenColumn
import com.example.manimanimani.ui.components.DataScreen.DataScreenSearchRow
import com.example.manimanimani.ui.components.DataScreen.DataScreenTabs
import com.example.manimanimani.ui.dialogManagers.DialogManager
import com.example.manimanimani.ui.dialogManagers.DialogState
import com.example.manimanimani.ui.dialogs.*
import com.example.manimanimani.ui.theme.*
import com.example.manimanimani.viewmodel.ReceiptViewModel
import kotlinx.coroutines.launch

@Composable
fun DataScreen(
    vm: ReceiptViewModel,
    dm: DialogManager,
    em: ErrorManager,
    navController: NavController,
    doHide: Boolean,
    setHide: (yes: Boolean) -> Unit
) {
    val receipts by vm.receipts.collectAsState()
    val reasons by vm.reasons.collectAsState()
    val tags by vm.tags.collectAsState()
    val tagToReasons by vm.tagToReasons.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )

    var selectedReceipt by remember {mutableStateOf(receiptZero)}
    var selectedReason by remember {mutableStateOf(reasonZero)}
    var selectedTag by remember {mutableStateOf(tagZero)}

    val scope = rememberCoroutineScope()

    var searchQuery by remember{mutableStateOf("")}

    val filteredReceipts =  receipts.filter { receipt ->
        searchQuery.isBlank() ||
        receipt.reasonText.contains(
            searchQuery,
            ignoreCase = true
        )
    }

    val filteredReasons = reasons.filter { reason ->
        searchQuery.isBlank() ||
        reason.name.contains(
            searchQuery,
            ignoreCase = true
        )
    }

    val filteredTags = tags.filter { tag ->
        searchQuery.isBlank() ||
        tag.text.contains(
            searchQuery,
            ignoreCase = true
        )
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedReceipt = receiptZero
        selectedReason = reasonZero
        selectedTag = tagZero
        searchQuery = ""
    }

    LaunchedEffect(searchQuery) {
        if (selectedReceipt !in filteredReceipts)
            selectedReceipt = receiptZero
        if (selectedReason !in filteredReasons)
            selectedReason = reasonZero
        if (selectedTag !in filteredTags)
            selectedTag = tagZero
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepCharcoal)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp)
            ) {
                DataScreenTabs(
                    getTab = {pagerState.currentPage},
                    setTab = {scope.launch {
                        pagerState.animateScrollToPage(it)
                    }}
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataScreenSearchRow(
                    searchQuery = searchQuery,
                    onSearchQueryChange = {searchQuery = it},
                    onClearQuery = {searchQuery = ""},
                    onSumClick = {dm.openDialog(DialogState.SUM)},
                    selectedTab = pagerState.currentPage
                )

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) { page ->
                    DataScreenColumn(
                        vm = vm,
                        dm = dm,
                        em = em,
                        selectedReceipt = selectedReceipt,
                        selectedReason = selectedReason,
                        selectedTag = selectedTag,
                        selectedTab = page,
                        receipts = filteredReceipts,
                        reasons = filteredReasons,
                        tags = filteredTags,
                        onClickReceipt = { selectedReceipt = it },
                        onClickReason = { selectedReason = it },
                        onClickTag = { selectedTag = it },
                        modifier = Modifier.fillMaxSize(),
                        doHide = doHide
                    )
                }
            }

            DataScreenAddButton(
                dm = dm,
                navController = navController,
                selectedTab = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            )
        }
    }

    DataScreenDialogs(
        vm = vm,
        dm = dm,
        em = em,
        scope = scope,
        selectedReceipt = selectedReceipt,
        selectedReason = selectedReason,
        selectedTag = selectedTag,
        receipts = receipts,
        tagToReasons = tagToReasons,
        onHide = { yes: Boolean -> setHide(yes) }
    )
}