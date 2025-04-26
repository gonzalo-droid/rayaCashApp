package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.BackgroundTertiaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import com.gondroid.rayacashapp.ui.core.components.CoinBottomSheet
import com.gondroid.rayacashapp.ui.core.components.ConfirmOrderBottomSheet
import com.gondroid.rayacashapp.ui.core.components.CustomTopBar
import com.gondroid.rayacashapp.ui.core.components.TextMedium
import com.gondroid.rayacashapp.ui.core.components.TextSmall
import com.gondroid.rayacashapp.ui.core.primaryBlack
import com.gondroid.rayacashapp.ui.core.primaryWhite
import com.gondroid.rayacashapp.ui.core.secondaryBlack
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rayacashapp.composeapp.generated.resources.Res
import rayacashapp.composeapp.generated.resources.ic_convert


@OptIn(KoinExperimentalAPI::class)
@Composable
fun ConvertScreenRoot(
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<ConvertScreenViewModel>()
    val state by viewModel.state.collectAsState()

    val fromTextField = state.fromCoinField

    LaunchedEffect(fromTextField) {
        snapshotFlow { fromTextField.text }
            .collect { newValue ->
                viewModel.convertAmount(newValue.toString())
            }
    }

    ConvertScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ConvertScreenAction.Back -> navigateBack()
                is ConvertScreenAction.GoToPreview -> {}
                is ConvertScreenAction.CoinFrom -> {
                    viewModel.saveCoinFrom(action.coin)
                }

                is ConvertScreenAction.CoinTo -> {
                    viewModel.saveCoinTo(action.coin)
                }

                is ConvertScreenAction.FilterCoin -> {
                    viewModel.filterCoins(action.isCoinFrom)
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertScreen(
    state: ConvertState,
    onAction: (ConvertScreenAction) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showConfirmOrderBottomSheet by remember { mutableStateOf(false) }
    var isCoinFrom by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundPrimaryColor,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                modifier = Modifier,
                title = "Convert",
                navigateStart = {
                    onAction(ConvertScreenAction.Back)
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp),
        ) {

            CardConvertFrom(
                modifier = Modifier.fillMaxWidth()
                    .background(color = BackgroundTertiaryColor, shape = RoundedCornerShape(8.dp))
                    .height(120.dp).padding(vertical = 30.dp, horizontal = 10.dp),
                state = state,
                onCoinSheet = {
                    isCoinFrom = true
                    showBottomSheet = true
                    onAction(ConvertScreenAction.FilterCoin(isCoinFrom = isCoinFrom))

                },
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(4.dp))
            Icon(
                modifier = Modifier.width(25.dp).height(25.dp).align(Alignment.CenterHorizontally),
                contentDescription = "",
                painter = painterResource(Res.drawable.ic_convert),
                tint = DefaultTextColor,
            )
            Spacer(modifier = Modifier.height(4.dp))

            CardConvertTo(
                modifier = Modifier.fillMaxWidth()
                    .background(color = BackgroundTertiaryColor, shape = RoundedCornerShape(8.dp))
                    .height(120.dp).padding(vertical = 20.dp, horizontal = 10.dp),
                state = state,
                onCoinSheet = {
                    isCoinFrom = false
                    showBottomSheet = true
                    onAction(ConvertScreenAction.FilterCoin(isCoinFrom = isCoinFrom))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            ButtonSection(modifier = Modifier.fillMaxWidth(), state = state, onPreview = {
                showConfirmOrderBottomSheet = true
            })

        }

        if (showBottomSheet) {
            CoinBottomSheet(
                state = state,
                sheetState = sheetState,
                onDismiss = { showBottomSheet = false },
                onItemSelect = { coin ->
                    if (isCoinFrom) {
                        onAction(ConvertScreenAction.CoinFrom(coin))
                    } else {
                        onAction(ConvertScreenAction.CoinTo(coin))
                    }
                }
            )
        }

        if (showConfirmOrderBottomSheet) {
            ConfirmOrderBottomSheet(
                state = state,
                sheetState = sheetState,
                onDismiss = { showConfirmOrderBottomSheet = false },
                onItemSelect = {

                }
            )
        }

    }
}


@Composable
fun CoinItem(modifier: Modifier, item: Coin, onCoinSelect: (Coin) -> Unit) {
    Row(
        modifier = modifier.clickable {
            onCoinSelect(item)
        }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier.width(27.dp).padding(end = 8.dp),
            contentDescription = item.name,
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
                text = item.currency.name, fontWeight = FontWeight.Normal, fontSize = 12.sp
            )
        }

    }
}

@Composable
fun CardConvertFrom(
    modifier: Modifier,
    state: ConvertState,
    onCoinSheet: () -> Unit,
    onAction: (ConvertScreenAction) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            TextSmall("From")
            Spacer(modifier = Modifier.weight(1f))
            TextSmall("Available ${state.balance.amount} ${state.balance.currency.name}")
        }
        Row {
            CoinSelect(
                modifier = Modifier,
                coin = state.fromCoinSelected,
                onCoinSheet = onCoinSheet
            )
            Spacer(modifier = Modifier.weight(1f))

            FieldCoinFrom(state = state, modifier = Modifier)
        }
    }
}

@Composable
fun CardConvertTo(
    modifier: Modifier,
    state: ConvertState,
    onCoinSheet: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(

            text = "To",
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Row {

            CoinSelect(
                modifier = Modifier,
                coin = state.toCoinSelected,
                onCoinSheet = onCoinSheet
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.amountConverted,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Normal,
            )

        }
    }
}

@Composable
fun CoinSelect(
    modifier: Modifier,
    coin: Coin,
    onCoinSheet: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier.width(25.dp),
            contentDescription = "",
            painter = painterResource(Coin.getCoinImage(currency = coin.currency)),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(4.dp))

        TextMedium(text = coin.currency.name)

        Box(
            modifier = Modifier.padding(4.dp).clickable {
                onCoinSheet()
            },
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Add Task",
                tint = DefaultTextColor,
            )

        }
    }
}


@Composable
fun FieldCoinFrom(
    state: ConvertState,
    modifier: Modifier,
) {
    BasicTextField(
        state = state.fromCoinField,
        cursorBrush = SolidColor(BackgroundPrimaryColor),
        lineLimits = TextFieldLineLimits.SingleLine,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.End,
        ),
        decorator = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                if (state.fromCoinField.text.toString().isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "0",
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                        color = secondaryBlack,
                    )
                } else {
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun ButtonSection(
    modifier: Modifier,
    state: ConvertState,
    onPreview: () -> Unit,
) {
    Button(
        enabled = state.canConvert,
        onClick = {
            onPreview()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.canConvert) {
                RayaColor
            } else {
                primaryBlack
            },
            contentColor = if (state.canConvert) {
                primaryWhite
            } else {
                primaryBlack
            }
        )
    ) {
        Text(
            text = "Preview",
        )
    }
}