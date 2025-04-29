package com.gondroid.rayacashapp.ui.home

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
import androidx.compose.material3.HorizontalDivider
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
import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import com.gondroid.rayacashapp.ui.core.components.CustomTopBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.ic_transactions


@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreenRoot(
    navigateToTransactions: () -> Unit
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadBalances()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HomeScreen(
        state = state,
        navigateToTransactions = navigateToTransactions
    )
}

@Composable
fun HomeScreen(state: HomeState, navigateToTransactions: () -> Unit) {
    Scaffold(
        containerColor = BackgroundPrimaryColor,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                modifier = Modifier,
                title = "Portfolio",
                navigateEnd = navigateToTransactions,
                iconNavigateEnd = painterResource(Res.drawable.ic_transactions)
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
                modifier =
                    Modifier
            ) {
                item {
                    TotalBalance(
                        modifier = Modifier.fillMaxWidth(),
                        state = state
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp))
                }

                items(
                    items = state.balances,
                    key = { balance -> balance.currency }
                ) { balance ->
                    CardItem(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp),
                        item = balance
                    )
                }


            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
fun TotalBalance(modifier: Modifier, state: HomeState) {
    Column(modifier = modifier) {
        Text(
            text = "Total Balance",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            color = DefaultTextColor,
        )
        Text(
            text = state.totalBalance,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DefaultTextColor,
        )
    }
}

@Composable
fun CardItem(modifier: Modifier, item: Balance) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(27.dp).padding(end = 8.dp),
            contentDescription = item.currency.name,
            painter = painterResource(item.currency.icon),
            alignment = Alignment.Center
        )

        Column {
            Text(
                text = item.currency.label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DefaultTextColor,
            )
            Text(
                text = item.currency.name,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = DefaultTextColor,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = item.amount.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DefaultTextColor,
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = item.amountToARS,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = DefaultTextColor,
            )
        }
    }
}