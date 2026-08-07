package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.logout_icon
import amobilekmm.shared.generated.resources.warning_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold15
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TransactionModel
import com.exquisite.dripp.core.components.LoadingDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

enum class TransactionFilter {
    ALL, RECEIVED, SENT
}

// Note: BalanceState and TransactionsState are in separate files in the same package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    savedStateHandle : SavedStateHandle,
    onBackClick: (() -> Unit)? = null,
    goToWebView: (String) -> Unit,
    viewModel: WalletViewModel = koinViewModel<WalletViewModel>(),
) {
    // Separate states for balance and transactions - no more conflicts!
    val balanceState by viewModel.balanceState.collectAsState()
    val transactionsState by viewModel.transactionsState.collectAsState()
    val topUpState = viewModel.topUptState.collectAsState()

    var selectedFilter by remember { mutableStateOf(TransactionFilter.ALL) }
    var balance by remember { mutableStateOf<BalanceModel?>(null) }
    var transactions by remember { mutableStateOf<List<TransactionModel>>(emptyList()) }
    var openFundWallet by remember { mutableStateOf(false) }
    var showErrorModal by remember { mutableStateOf(false) }

    // Error handling states
    var balanceError by remember { mutableStateOf<String?>(null) }
    var transactionError by remember { mutableStateOf<String?>(null) }

    // Wallet funding states
    var fundingAmount by remember { mutableStateOf("") } // Raw numeric value
    var formattedAmount by remember { mutableStateOf("") } // Formatted display value
    var amountError by remember { mutableStateOf<String?>(null) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Load data on first composition
    LaunchedEffect(Unit) {
        viewModel.getCustomerBalance()
        viewModel.getCustomerTransactions()
    }

    LaunchedEffect(Unit) {
        savedStateHandle.getStateFlow<String?>("transaction_id", null).collect { transactionId ->
            if (!transactionId.isNullOrEmpty()) {
                viewModel.completeTopUpAccount(
                    txnRef = transactionId
                )
            }
        }
    }

    // Update balance from separate state - no conflicts!
    LaunchedEffect(balanceState) {
        when (val state = balanceState) {
            is BalanceState.Success -> {
                balance = state.data
                balanceError = null // Clear error on success
            }
            is BalanceState.Error -> {
                balanceError = state.message
            }
            else -> {}
        }
    }

    // Update transactions from separate state - no conflicts!
    LaunchedEffect(transactionsState) {
        when (val state = transactionsState) {
            is TransactionsState.Success -> {
                transactions = state.data
                transactionError = null // Clear error on success
            }
            is TransactionsState.Error -> {
                transactionError = state.message
            }
            else -> {}
        }
    }

    when(val result = topUpState.value){
        is TopUpState.Idle ->{}

        is TopUpState.Loading ->{
            LoadingDialog(true)
        }

        is TopUpState.Error ->{
            showErrorModal = true

            GenericAlertModal(
                modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
                title = "Oops!",
                message = result.message,
                primaryButton = ModalButton(
                    text = "Ok",
                    backgroundColor = Color(0xFF10B981), // Green
                    action = {
                        showErrorModal = false
                    }
                )
            )
        }

        is TopUpState.CompleteTopUpSuccess ->{
            balance = result.data
            LaunchedEffect(result) {
                viewModel.getCustomerBalance()
                viewModel.getCustomerTransactions()
            }

        }

        is TopUpState.InitTopUpSuccess ->{
            viewModel.clearTopUpState()
            goToWebView(result.data.paymentLink)
        }
    }

    // Filter transactions based on selected tab
    val filteredTransactions = remember(transactions, selectedFilter) {
        when (selectedFilter) {
            TransactionFilter.ALL -> transactions
            TransactionFilter.RECEIVED -> transactions.filter { it.drcr.uppercase() == "CR" }
            TransactionFilter.SENT -> transactions.filter { it.drcr.uppercase() == "DR" }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF2F6))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Section (Orange background)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF29100))
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Top Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    Text(
                        text = "Wallet",
                        style = getPoppinsBold18(),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Balance Section
                Text(
                    text = "Available Balance",
                    style = getPoppinsMedium13(),
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "₦${(balance?.balance ?: 0.0).formatBalance()}",
                    style = getPoppinsBold18().copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Fund Wallet Button
                Button(
                    onClick = { openFundWallet = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFFF29100),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Fund Wallet",
                        style = getPoppinsBold14(),
                        color = Color(0xFFF29100)
                    )
                }
            }

            // Tab Navigation
            TabRow(
                selectedTabIndex = selectedFilter.ordinal,
                containerColor = Color.White,
                contentColor = Color(0xFFF29100),
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedFilter.ordinal]),
                        color = Color.Transparent
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                TransactionFilter.entries.forEachIndexed { index, filter ->
                    val isSelected = selectedFilter == filter

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) Color(0xFFFFF7ED) else Color.Transparent
                            )
                            .clickable { selectedFilter = filter }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (filter) {
                                TransactionFilter.ALL -> "All"
                                TransactionFilter.RECEIVED -> "Received"
                                TransactionFilter.SENT -> "Sent"
                            },
                            style = getPoppinsSemiBold12(),
                            color = if (isSelected) Color(0xFFF29100) else Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Content - Show loading if either balance or transactions are loading
            val isLoading = balanceState is BalanceState.Loading || transactionsState is TransactionsState.Loading

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFF29100),
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                // Show content when not loading
                if (filteredTransactions.isEmpty()) {
                    EmptyTransactionsState(filter = selectedFilter)
                } else {
                    Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            // Section Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Recent Transactions",
                                    style = getPoppinsBold16(),
                                    color = Color(0xFF1E293B)
                                )

                                TextButton(onClick = { /* TODO: View all */ }) {
                                    Text(
                                        text = "View all",
                                        style = getPoppinsSemiBold13(),
                                        color = Color(0xFFF29100)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Transactions List
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(filteredTransactions) { transaction ->
                                    TransactionItem(transaction = transaction)
                                }
                            }
                        }
                }
            }

            if(openFundWallet){
                ModalBottomSheet(
                    onDismissRequest = {
                        openFundWallet = false
                        fundingAmount = ""
                        formattedAmount = ""
                        amountError = null
                    },
                    sheetState = sheetState,
                    containerColor = Color(0xFFFFFFFF),
                    contentColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Wallet Icon
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color(0xFFF29100),
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    Color(0xFFFFF7ED),
                                    shape = RoundedCornerShape(32.dp)
                                )
                                .padding(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title
                        Text(
                            text = "Fund Your Wallet",
                            style = getPoppinsBold20().copy(fontSize = 22.sp),
                            color = Color(0xFF000000)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Description
                        Text(
                            text = "Enter amount to top up your wallet",
                            style = getPoppinsMedium13(),
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Amount Input Field
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Amount (₦)",
                                style = getPoppinsSemiBold14(),
                                color = Color(0xFF1E293B),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TextField(
                                value = formattedAmount,
                                onValueChange = { newValue ->
                                    // Remove existing formatting (commas)
                                    val rawValue = newValue.replace(",", "")

                                    // Only allow numbers (no decimal for currency)
                                    if (rawValue.isEmpty() || rawValue.matches(Regex("^\\d*$"))) {
                                        fundingAmount = rawValue // Store raw value
                                        formattedAmount = formatCurrencyInput(rawValue) // Display formatted

                                        // Validate on change using raw value
                                        amountError = validateAmount(rawValue)
                                    }
                                },
                                placeholder = {
                                    Text(
                                        text = "0",
                                        style = getPoppinsMedium13(),
                                        color = Color(0xFF94A3B8)
                                    )
                                },
                                prefix = {
                                    Text(
                                        text = "₦ ",
                                        style = getPoppinsSemiBold14(),
                                        color = Color(0xFF1E293B)
                                    )
                                },
                                isError = amountError != null,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedIndicatorColor = Color(0xFFF29100),
                                    unfocusedIndicatorColor = Color(0xFFE2E8F0),
                                    errorContainerColor = Color(0xFFFEF2F2),
                                    errorIndicatorColor = Color(0xFFEF4444)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Error message
                            if (amountError != null) {
                                Text(
                                    text = amountError!!,
                                    style = getPoppinsMedium13(),
                                    color = Color(0xFFEF4444),
                                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                                )
                            }

                            // Minimum amount hint
                            if (amountError == null) {
                                Text(
                                    text = "Minimum amount: ₦100",
                                    style = getPoppinsMedium13(),
                                    color = Color(0xFF94A3B8),
                                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Fund Button
                        Button(
                            onClick = {
                                val error = validateAmount(fundingAmount)
                                if (error == null) {
                                    // Initiate wallet funding with RAW value (no commas)
                                    scope.launch {
                                        val amountAsInt = fundingAmount.toIntOrNull() ?: 0
                                        viewModel.initTopUpAccount(amountAsInt)
                                        sheetState.hide()
                                        openFundWallet = false
                                        fundingAmount = ""
                                        formattedAmount = ""
                                        amountError = null
                                    }
                                } else {
                                    amountError = error
                                }
                            },
                            enabled = fundingAmount.isNotEmpty() && amountError == null,
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF29100),
                                disabledContainerColor = Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(
                                text = "Proceed to Payment",
                                color = Color.White,
                                style = getPoppinsBold16()
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Cancel Button
                        Button(
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                    openFundWallet = false
                                    fundingAmount = ""
                                    formattedAmount = ""
                                    amountError = null
                                }
                            },
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .border(
                                    width = 1.5.dp,
                                    color = Color(0xFF2D2D2D),
                                    shape = RoundedCornerShape(25.dp)
                                )
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color(0xFF2D2D2D),
                                style = getPoppinsBold16()
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            // Balance Error Modal
            if (balanceError != null) {
                GenericAlertModal(
                    modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
                    title = "Balance Error",
                    message = balanceError ?: "Failed to load wallet balance. Please try again.",
                    primaryButton = ModalButton(
                        text = "Retry",
                        backgroundColor = Color(0xFFF29100), // Orange
                        action = {
                            balanceError = null
                            viewModel.getCustomerBalance()
                        }
                    ),
                    secondaryButton = ModalButton(
                        text = "Dismiss",
                        backgroundColor = Color.Transparent,
                        textColor = Color(0xFF64748B),
                        action = {
                            balanceError = null
                        }
                    )
                )
            }

            // Transactions Error Modal
            if (transactionError != null) {
                GenericAlertModal(
                    modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
                    title = "Transactions Error",
                    message = transactionError ?: "Failed to load transactions. Please try again.",
                    primaryButton = ModalButton(
                        text = "Retry",
                        backgroundColor = Color(0xFFF29100), // Orange
                        action = {
                            transactionError = null
                            viewModel.getCustomerTransactions()
                        }
                    ),
                    secondaryButton = ModalButton(
                        text = "Dismiss",
                        backgroundColor = Color.Transparent,
                        textColor = Color(0xFF64748B),
                        action = {
                            transactionError = null
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun ShowLoader() {
    TODO("Not yet implemented")
}

@Composable
private fun TransactionItem(transaction: TransactionModel) {
    val isCredit = transaction.drcr.uppercase() == "CR"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable { /* TODO: View transaction details */ }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isCredit) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isCredit) "↙" else "↗",
                style = getPoppinsBold18(),
                color = if (isCredit) Color(0xFF10B981) else Color(0xFFEF4444)
            )
        }

        // Transaction Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.narration,
                style = getPoppinsSemiBold14(),
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = transaction.createdAt.formatToReadableDate(),
                style = getPoppinsBold11(),
                color = Color(0xFF64748B)
            )
        }

        // Amount
        Text(
            text = "${if (isCredit) "+" else "-"}₦${(transaction.amount).formatBalance()}",
            style = getPoppinsBold15(),
            color = if (isCredit) Color(0xFF10B981) else Color(0xFFEF4444)
        )
    }
}

@Composable
private fun EmptyTransactionsState(filter: TransactionFilter) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = when (filter) {
                    TransactionFilter.ALL -> "No Transactions Yet"
                    TransactionFilter.RECEIVED -> "No Received Transactions"
                    TransactionFilter.SENT -> "No Sent Transactions"
                },
                style = getPoppinsBold18(),
                color = Color(0xFF1E293B)
            )
            Text(
                text = when (filter) {
                    TransactionFilter.ALL -> "Your transaction history will appear here"
                    TransactionFilter.RECEIVED -> "Received payments will appear here"
                    TransactionFilter.SENT -> "Sent payments will appear here"
                },
                style = getPoppinsSemiBold14(),
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}


private fun formatTransactionDate(dateString: String): String {
    return try {
        // Parse and format date
        // Expected format: "Sept 23, 2023 • 02:30 PM"
        val parts = dateString.split(" ")
        if (parts.size >= 2) {
            "${parts[0]} • ${parts[1]}"
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}

/**
 * Formats currency input with thousand separators
 * Example: "50000" -> "50,000"
 * @param rawValue The raw numeric string
 * @return Formatted string with commas
 */
private fun formatCurrencyInput(rawValue: String): String {
    if (rawValue.isEmpty()) return ""

    return try {
        // Add thousand separators
        val number = rawValue.toLongOrNull() ?: return rawValue
        number.toString()
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
    } catch (e: Exception) {
        rawValue
    }
}

/**
 * Validates the funding amount input
 * @return null if valid, error message if invalid
 */
private fun validateAmount(amount: String): String? {
    return when {
        amount.isEmpty() -> "Amount is required"
        amount.toLongOrNull() == null -> "Invalid amount"
        amount.toLong() < 100 -> "Minimum amount is ₦100"
        amount.toLong() > 1000000 -> "Maximum amount is ₦1,000,000"
        else -> null
    }
}
