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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import com.gondroid.rayacashapp.ui.core.components.CustomTopBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.ic_convert


@OptIn(KoinExperimentalAPI::class)
@Composable
fun TransactionScreenRoot(
    navigateBack: () -> Unit,
    navigateToConvert: () -> Unit
) {
    val viewModel = koinViewModel<TransactionScreenViewModel>()
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadTransactions() // recarga los datos manualmente
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
                title = "Transaction History",
                iconNavigateEnd = painterResource(Res.drawable.ic_convert)
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier,
            ) {
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

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = RayaColor
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "From",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(end = 8.dp),
                    color = DefaultTextColor,
                )
                Image(
                    modifier = Modifier.width(15.dp),
                    contentDescription = "",
                    painter = painterResource(item.fromCurrency.icon),
                    alignment = Alignment.Center
                )
            }
            Text(
                text = "${item.fromAmount} ${item.fromCurrency}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DefaultTextColor,
            )
            Text(
                text = item.date,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = DefaultTextColor,
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
                    text = "To",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(end = 8.dp),
                    color = DefaultTextColor,
                )
                Image(
                    modifier = Modifier.width(15.dp),
                    contentDescription = "",
                    painter = painterResource(item.toCurrency.icon),
                    alignment = Alignment.CenterEnd
                )
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "${item.toAmount} ${item.toCurrency}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DefaultTextColor,
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = item.status.name,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = DefaultTextColor,
            )
        }
    }
}