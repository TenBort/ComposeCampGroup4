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
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage

@Composable
fun Jar(
    jar: Jar,
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit,
    onFavoriteClick: (Jar) -> Unit,
    onDonateClick: (Jar) -> Unit
) {
    val savedAmount by remember {
        mutableLongStateOf(jar.amount)
    }
    val savedGoal by remember {
        mutableLongStateOf(jar.goal)
    }
    val percentage = if (savedGoal == 0L) {
        1f
    } else {
        savedAmount / savedGoal.toFloat()
    }



    Column(modifier = modifier.fillMaxWidth()) {
        JarInfo(jar = jar, Modifier.fillMaxWidth(), onFavoriteClick, onCopyClick)

        JarProgressBar(percentage, jar.closed)

        ExpandableSection(modifier = Modifier.fillMaxWidth(), isClosed = jar.closed) {
            ExpendedJarContent(jar, Modifier, onDonateClick)
        }
    }
}

@Composable
fun JarInfo(
    jar: Jar, modifier: Modifier = Modifier, onFavoriteClick: (Jar) -> Unit, onCopyClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
            ), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top
    ) {

        OwnerJarImage(
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(70.dp)
                .align(Alignment.CenterVertically), imageUri = Uri.parse(jar.ownerIcon)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, top = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = jar.ownerName,
                modifier = Modifier.wrapContentSize(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = jar.description,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )


            Row(Modifier.padding(top = 16.dp)) {
                Text(
                    text = if (jar.closed) stringResource(id = R.string.jar_closed) else stringResource(
                        id = R.string.jar_active
                    ),
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                if (jar.goal == 0L) {
                    Text(
                        text = stringResource(id = R.string.jar_no_goal),
                        modifier = Modifier
                            .padding(start = 10.dp)
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
            Modifier.padding(top = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = if (jar.isFavorite) painterResource(id = R.drawable.ic_star_solid) else painterResource(
                    id = R.drawable.ic_star_border
                ),
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
                        //TODO what we need to do on click ?
                        onCopyClick.invoke()
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
                money = jar.amount,
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
                    money = jar.goal,
                    currency = jar.currency
                )
            }
        }

        RoundedButton(
            text = stringResource(id = R.string.jar_donate),
            onClick = { onDonateClick.invoke(jar) })
    }
}

@Composable
fun ExpandableSection(
    modifier: Modifier = Modifier, isClosed: Boolean, content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(interactionSource = interactionSource, indication = null) {
                isExpanded = !isExpanded
            }
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)
            )
            .fillMaxWidth(),
    ) {
        Text(
            text = if (isExpanded) stringResource(id = R.string.hide_details) else stringResource(id = R.string.show_details),
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 24.dp),
            fontSize = 12.sp,
            color = if (isClosed) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline
        )

        AnimatedVisibility(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)
                )
                .fillMaxWidth(), visible = isExpanded
        ) {
            content()
        }
    }
}

@Composable
fun MoneyJarText(
    modifier: Modifier = Modifier, painter: Painter, title: String, money: Long, currency: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = "icon",
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
        ) {
            Text(
                text = title,
                modifier = Modifier,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = "$money $currency",
                modifier = Modifier,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun RoundedButton(
    text: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
private fun JarProgressBar(
    progress: Float, isClosed: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .height(15.dp),
            progress = { if (isClosed) 1f else progress },
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.onPrimary,
            color = if (isClosed) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
fun ExpandableSectionPreview() {
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