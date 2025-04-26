package com.gondroid.rayacashapp.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.ui.convert.CoinItem
import com.gondroid.rayacashapp.ui.convert.ConvertState
import com.gondroid.rayacashapp.ui.core.BackgroundPrimaryColor
import com.gondroid.rayacashapp.ui.core.DefaultTextColor
import com.gondroid.rayacashapp.ui.core.RayaColor
import com.gondroid.rayacashapp.ui.core.primaryWhite
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinBottomSheet(
    state: ConvertState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onItemSelect: (Coin) -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundPrimaryColor,
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Coins",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.padding(8.dp).clickable {
                        onDismiss()
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Add Task",
                    tint = DefaultTextColor,
                )

            }

            state.coins.forEach { coin ->
                CoinItem(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), item = coin
                ) { coin ->
                    onItemSelect(coin)
                    onDismiss()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmOrderBottomSheet(
    state: ConvertState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onItemSelect: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        containerColor = BackgroundPrimaryColor,
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 360.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter).fillMaxSize(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Confirm Order",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        modifier = Modifier.padding(8.dp).clickable {
                            onDismiss()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Add Task",
                        tint = DefaultTextColor,
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.width(27.dp).padding(end = 8.dp),
                            contentDescription = "",
                            painter = painterResource(Coin.getCoinImage(state.fromCoinSelected.currency)),
                            alignment = Alignment.Center
                        )

                        TextSmall(text = "From")
                        TextMedium(text = "${state.fromCoinField.text} ${state.fromCoinSelected.currency.name}")
                    }

                    Icon(
                        modifier = Modifier.padding(8.dp).clickable {
                            onDismiss()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Add Task",
                        tint = DefaultTextColor,
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.width(27.dp).padding(end = 8.dp),
                            contentDescription = "",
                            painter = painterResource(Coin.getCoinImage(state.toCoinSelected.currency)),
                            alignment = Alignment.Center
                        )

                        TextSmall(text = "To")
                        TextMedium(text = "${state.amountConverted} ${state.toCoinSelected.currency.name}")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    TextSmall("Type")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Instant", fontWeight = FontWeight.Normal, fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    TextSmall("Transaction Fees")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "0 ${state.toCoinSelected.currency.name}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }

            Button(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                enabled = state.canConvert,
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RayaColor,
                    contentColor = primaryWhite
                )
            ) {
                Text(
                    text = "Confirmar",
                )
            }
        }
    }
}