package com.gondroid.rayacashapp.ui.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.Currency
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import com.gondroid.rayacashapp.ui.core.components.CustomTopBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun TransactionScreenRoot(
    navigateBack: () -> Unit,
    navigateToConvert: () -> Unit
) {
    val viewModel = koinViewModel<TransactionScreenViewModel>()
    val state by viewModel.state.collectAsState()
    TransactionScreen(
        state = state,
        navigateToConvert = navigateToConvert,
        navigateBack = navigateBack
    )
}

@Composable
fun TransactionScreen(
    state: TransactionState,
    navigateToConvert: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundPrimaryColor,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                modifier = Modifier,
                navigateEnd = navigateToConvert,
                navigateStart = navigateBack,
                title = "Transaction History"
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
        ) {

            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = RayaColor
                        )
                    }
                }
            } else {
                items(
                    items = state.transactions,
                    key = { transaction -> transaction.id }
                ) { transaction ->
                    CardItemTransaction(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp),
                        item = transaction
                    )
                }
            }

        }
    }
}

@Composable
fun CardItemTransaction(modifier: Modifier, item: Transaction) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        Column {
            Row {
                Text(
                    text = "from",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Image(
                    modifier = Modifier.width(15.dp),
                    contentDescription = "",
                    painter = painterResource(Coin.getCoinImage(currency = Currency.USD)),
                    alignment = Alignment.Center
                )
            }
            Text(
                text = "0.607 DOGE",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "02/12/2025 11:12:13",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "to",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(end = 8.dp),
                )
                Image(
                    modifier = Modifier.width(15.dp),
                    contentDescription = "",
                    painter = painterResource(Coin.getCoinImage(currency = Currency.USD)),
                    alignment = Alignment.CenterEnd
                )
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "0.00000116 BTC",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "Completed",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}