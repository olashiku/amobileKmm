package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold15
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TransactionModel
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

enum class TransactionFilter {
    ALL, RECEIVED, SENT
}

@Composable
fun WalletScreen(
    onBackClick: (() -> Unit)? = null,
    onFundWallet: (() -> Unit)? = null,
    viewModel: WalletViewModel = koinViewModel<WalletViewModel>(),
    dataStore: AMobileDataStore = koinInject<AMobileDataStore>()
) {
    val state by viewModel.walletState.collectAsState()
    var selectedFilter by remember { mutableStateOf(TransactionFilter.ALL) }
    var balance by remember { mutableStateOf<BalanceModel?>(null) }
    var transactions by remember { mutableStateOf<List<TransactionModel>>(emptyList()) }

    // Load data on first composition
    LaunchedEffect(Unit) {
        viewModel.getCustomerBalance()
        viewModel.getCustomerTransactions()
    }

    // Update local state based on VM state
    LaunchedEffect(state) {
        when (state) {
            is WalletState.GetBalanceSuccess -> {
                balance = (state as WalletState.GetBalanceSuccess).data
            }
            is WalletState.GetTransactionsSuccess -> {
                transactions = (state as WalletState.GetTransactionsSuccess).data
            }
            is WalletState.CompleteTopUpSuccess -> {
                balance = (state as WalletState.CompleteTopUpSuccess).data
            }
            else -> {}
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
                    text = (balance?.balance ?: 0.0).formatBalance(),
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
                    onClick = { onFundWallet?.invoke() },
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

            // Content
            when (state) {
                is WalletState.Loading -> {
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
                }

                else -> {
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
            }
        }
    }
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
                text = formatTransactionDate(transaction.createdAt),
                style = getPoppinsBold11(),
                color = Color(0xFF64748B)
            )
        }

        // Amount
        Text(
            text = "${if (isCredit) "+" else "-"}${(transaction.amount).formatBalance()}",
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
