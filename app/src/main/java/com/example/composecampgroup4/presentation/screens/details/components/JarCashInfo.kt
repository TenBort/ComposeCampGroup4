package com.example.composecampgroup4.presentation.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import java.util.Currency
import java.util.Locale

@Composable
fun JarCashInfo(
    modifier: Modifier = Modifier,
    amount: Long,
    goal: Long,
    currency: Int,
    jarClosed: Boolean
) {
    var progress = 1.0f

    if (goal != 0L) {
        progress = amount.toFloat() / goal.toFloat()
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 28.dp, bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JarCashBox(
                painter = painterResource(id = R.drawable.ic_jar_amount),
                title = stringResource(id = R.string.jar_accumulated),
                amount = amount,
                currency = currency
            )
            if (goal > 0) {
                Spacer(modifier = Modifier.width(if (goal < 100000000000) 20.dp else 16.dp))
                VerticalDivider(
                    modifier = Modifier
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(if (goal < 100000000000) 20.dp else 16.dp))
                JarCashBox(
                    painter = painterResource(id = R.drawable.ic_jar_goal),
                    title = stringResource(id = R.string.jar_goal),
                    amount = goal,
                    currency = currency
                )
            }
        }

        LinearProgressIndicator(
            progress = { if (jarClosed) 1f else progress },
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.background,
            color = if (jarClosed) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.primary,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(8.dp)
        )
    }
}

@Composable
private fun JarCashBox(
    painter: Painter,
    title: String,
    amount: Long,
    currency: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
            modifier = modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            val currencySymbol = getCurrencySymbol(currency)
            val formattedAmount = String.format(Locale.getDefault(),"%,.2f", amount / 100.0)
            Text(
                text = title,
                fontSize = 14.sp,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.outline,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$formattedAmount $currencySymbol",
                fontSize = 14.sp,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun getCurrencySymbol(numericCode: Int): String? {
    val currency = Currency.getAvailableCurrencies().firstOrNull { it.numericCode == numericCode }
    return currency?.symbol
}