package com.gondroid.rayacashapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreenRoot(
    navigateToTransactions: () -> Unit
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state
    )
}

@Composable
fun HomeScreen(state: HomeState) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            text = "Portafolio",
                            color = BackgroundPrimaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    Spacer(modifier = Modifier.width(24.dp))
                },
                actions = {
                    Box(
                        modifier =
                            Modifier
                                .padding(8.dp)
                                .clickable {

                                },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Add Task",
                            tint = BackgroundPrimaryColor,
                        )

                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(vertical = 16.dp),
        ) {

            item {
                TotalBalance(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    totalBalance = state.totalBalance
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
            items(
                items = state.balances,
                key = { balance -> balance.currency }
            ) { balance ->
                CardItem(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    item = balance
                )
            }
        }
    }
}

@Composable
fun TotalBalance(modifier: Modifier, totalBalance: String) {
    Column(modifier = modifier) {
        Text(text = "Total Balance", modifier = Modifier.fillMaxWidth(), fontSize = 14.sp)
        Text(
            text = "ARS 123123.23",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
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
            modifier = Modifier.width(25.dp).padding(end = 8.dp),
            contentDescription = item.currency.name,
            painter = painterResource(Coin.getCoinImage(item.currency)),
            alignment = Alignment.Center
        )

        Column {
            Text(
                text = Coin.getCoin(item.currency).name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = item.currency.name,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = item.amount.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "ARS 123123.23",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}