package com.example.composecampgroup4.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.utils.getCurrencySymbol
import java.util.Locale

@Composable
fun MoneyJarInfo(
    modifier: Modifier = Modifier,
    jar: Jar,
    fontSize: TextUnit,
    iconSize: Dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center
    ) {
        // AccumulatedText
        MoneyJarText(
            painter = painterResource(id = R.drawable.ic_jar_amount),
            title = stringResource(id = R.string.jar_accumulated),
            currency = jar.currency,
            money = jar.amount,
            fontSize = fontSize,
            iconSize = iconSize
        )

        if (jar.goal > 0) {
            Spacer(modifier = Modifier.width(16.dp))
            VerticalDivider(
                modifier = Modifier
                    .width(1.dp),
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.width(16.dp))

            // GoalText
            MoneyJarText(
                painter = painterResource(id = R.drawable.ic_jar_goal),
                title = stringResource(id = R.string.jar_goal),
                currency = jar.currency,
                money = jar.goal,
                fontSize = fontSize,
                iconSize = iconSize
            )
        }
    }
}

@Composable
private fun MoneyJarText(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    currency: Int,
    money: Long,
    fontSize: TextUnit = TextUnit.Unspecified,
    iconSize: Dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
            modifier = modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            val currencySymbol = getCurrencySymbol(currency)
            val formattedAmount = String.format(Locale.getDefault(),"%,.2f", money / 100.0)

            // TitleText
            Text(
                text = title,
                fontSize = fontSize,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.outline,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // AmountText
            Text(
                text = "$formattedAmount $currencySymbol",
                fontSize = fontSize,
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