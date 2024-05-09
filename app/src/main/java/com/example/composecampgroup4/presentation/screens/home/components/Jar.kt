package com.example.composecampgroup4.presentation.screens.home.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage
import com.example.composecampgroup4.presentation.core.utils.getCurrencySymbol
import java.util.Locale

@Composable
fun Jar(
    jar: Jar,
    modifier: Modifier = Modifier,
    onCopyClick: (String) -> Unit,
    onJarClick: (String) -> Unit,
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
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onJarClick(jar.jarId)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    ) {
        JarInfo(jar = jar, Modifier.fillMaxWidth(), onFavoriteClick, onCopyClick)

        JarProgressBar(percentage, modifier.fillMaxWidth(), jar.closed)

        ExpandableSection(
            modifier = Modifier.fillMaxWidth(),
            isClosed = jar.closed
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
                .padding(start = 16.dp)
                .size(70.dp)
                .align(Alignment.CenterVertically),
            imageUri = Uri.parse(jar.ownerIcon),
            isClosed = jar.closed
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, top = 16.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = jar.title,
                modifier = Modifier
                    .wrapContentSize(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 20.sp
            )
            Text(
                text = stringResource(R.string.collected_by_owner_name, jar.ownerName),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 6.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 18.sp
            )


            Row(Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = if (isClosed) stringResource(id = R.string.jar_closed) else stringResource(
                        id = R.string.jar_active
                    ),
                    color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .background(
                            if (isClosed) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else {
                                MaterialTheme.colorScheme.background
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp)
                )

                if (jar.goal == 0L) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.jar_no_goal),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .background(
                                if (isClosed) {
                                    MaterialTheme.colorScheme.surfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.background
                                },
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp)
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
                imageVector = if (jar.isFavourite) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = "favourite Jar",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        onFavoriteClick.invoke(jar)
                    }
            )

            Icon(
                imageVector = Icons.Outlined.FileCopy,
                contentDescription = "copy Jar",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        onCopyClick.invoke(jar.jarId)
                    }
                    .padding(top = 4.dp)
                    .size(22.dp)
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
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
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
            fontSize = 14.sp,
            color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        )

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = isExpanded,
            enter = fadeIn() + expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut() + shrinkVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
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
    val formattedAmount = String.format(Locale.getDefault(), "%,.2f", amount / 100.0)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = "icon",
            modifier = Modifier
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
                style = TextStyle(
                    color = MaterialTheme.colorScheme.outline,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
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
            .padding(vertical = 16.dp, horizontal = 24.dp),
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
                .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                .height(8.dp),
            progress = { if (isClosed) 1f else progress },
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.background,
            color = if (isClosed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
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
    }, {})
}