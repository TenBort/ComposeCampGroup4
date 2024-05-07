package com.example.composecampgroup4.presentation.screens.home.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage
import com.example.composecampgroup4.presentation.core.utils.getCurrencySymbol

@Composable
fun Jar(
    jar: Jar,
    modifier: Modifier = Modifier,
    onCopyClick: (String) -> Unit,
    onFavoriteClick: (Jar) -> Unit,
    onDonateClick: (Jar) -> Unit
) {
    val percentage = if (jar.goal == 0L) {
        1f
    } else {
        jar.amount / jar.goal.toFloat()
    }

    Card(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        JarInfo(jar = jar, Modifier.fillMaxWidth(), onFavoriteClick, onCopyClick)

        JarProgressBar(percentage, modifier.fillMaxWidth(), jar.closed)

        ExpandableSection(
            modifier = Modifier.fillMaxWidth(),
            isClosed = jar.closed,
            isExpended = jar.isExpanded
        ) {
            ExpendedJarContent(jar, Modifier, onDonateClick)
        }
    }
}

@Composable
fun JarInfo(
    jar: Jar, modifier: Modifier = Modifier, onFavoriteClick: (Jar) -> Unit, onCopyClick: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isClosed = jar.closed

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        OwnerJarImage(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .size(70.dp)
                .align(Alignment.CenterVertically),
            imageUri = Uri.parse(jar.ownerIcon)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = jar.ownerName + " " + stringResource(id = R.string.jar_owner_additions),
                modifier = Modifier.wrapContentSize(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = jar.description,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )


            Row(Modifier.padding(top = 16.dp)) {
                Text(
                    text = if (isClosed) stringResource(id = R.string.jar_closed) else stringResource(
                        id = R.string.jar_active
                    ),
                    color = if (isClosed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .background(
                            if (isClosed) {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            } else {
                                MaterialTheme.colorScheme.onPrimary
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )

                if (jar.goal == 0L) {
                    Text(
                        text = stringResource(id = R.string.jar_no_goal),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

            }
        }

        Column(
            Modifier.padding(top = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = if (jar.isFavourite) painterResource(id = R.drawable.ic_star_solid) else painterResource(id = R.drawable.ic_star_border),
                contentDescription = "copy Jar",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        onFavoriteClick.invoke(jar)
                    }
                    .size(24.dp),
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_jar_copy),
                contentDescription = "copy Jar",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        onCopyClick.invoke(jar.jarId)
                    }
                    .padding(top = 4.dp)
                    .size(20.dp),
            )
        }
    }
}

@Composable
fun ExpendedJarContent(
    jar: Jar, modifier: Modifier = Modifier, onDonateClick: (Jar) -> Unit = {}
) {
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoneyJarText(
                painter = painterResource(
                    id = R.drawable.ic_jar_amount
                ),
                title = stringResource(id = R.string.jar_accumulated),
                amount = jar.amount,
                currency = jar.currency
            )

            if (jar.goal != 0L) {
                VerticalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(30.dp)
                        .width(1.dp)
                )

                MoneyJarText(
                    painter = painterResource(
                        id = R.drawable.ic_jar_goal
                    ),
                    title = stringResource(id = R.string.jar_goal),
                    amount = jar.goal,
                    currency = jar.currency
                )
            }
        }

        RoundedButton(
            text = stringResource(id = R.string.jar_donate),
            isEnabled = !jar.closed,
            onClick = { onDonateClick.invoke(jar) })
    }
}

@Composable
fun ExpandableSection(
    modifier: Modifier = Modifier,
    isClosed: Boolean,
    isExpended: Boolean = false,
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(isExpended) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(interactionSource = interactionSource, indication = null) {
                isExpanded = !isExpanded
            }
            .fillMaxWidth(),
    ) {
        Text(
            text = if (isExpanded) stringResource(id = R.string.hide_details) else stringResource(id = R.string.show_details),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            fontSize = 12.sp,
            color = if (isClosed) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
        )

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = isExpanded
        ) {
            content()
        }
    }
}

@Composable
fun MoneyJarText(
    modifier: Modifier = Modifier, painter: Painter, title: String, amount: Long, currency: Int
) {
    val currencySymbol = getCurrencySymbol(currency)
    val formattedAmount = String.format("%,.2f", amount / 100.0)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = "icon",
            modifier = Modifier
                .padding(end = 4.dp)
                .size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                modifier = Modifier,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = "$formattedAmount $currencySymbol",
                modifier = Modifier,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
private fun JarProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    isClosed: Boolean
) {
    Column(
        modifier = modifier
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(16.dp),
            progress = { if (isClosed) 1f else progress },
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.onPrimary,
            color = if (isClosed) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
fun JarPreview() {
    val jar = remember {
        mutableStateOf(
            Jar(
                "",
                "",
                600L,
                10000L,
                "https://cdn-icons-png.flaticon.com/512/4600/4600333.png",
                "title",
                "ownerName",
                0,
                "description",
                false,
                "",
                true
            )
        )
    }

    Jar(jar = jar.value, Modifier.fillMaxWidth(), {}, {}, {
        val isClosed = jar.value.closed != jar.value.closed
        jar.value = jar.value.copy(closed = isClosed)
    })
}