package com.example.composecampgroup4.presentation.screens.details

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage
import com.example.composecampgroup4.presentation.core.components.TopBarApp
import com.example.composecampgroup4.presentation.screens.details.components.DetailsBottomButton
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme
import java.util.Currency

@Composable
fun DetailsScreenRoot(navigationState: NavigationState, jarId: String) {
    val viewModel =
        hiltViewModel<DetailsViewModel, DetailsViewModel.DetailsViewModelFactory> { factory ->
            factory.create(jarId)
        }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val localUriHandler = LocalUriHandler.current

    BaseContentLayout(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
        topBar = {
            TopBarApp(
                title = stringResource(R.string.jar_details),
                onBackClick = { navigationState.popBackStack() }
            )
        },
        bottomBar = { uiState ->
            DetailsBottomButton(
                modifier = Modifier.padding(start = 24.dp, end = 8.dp, bottom = 16.dp),
                onButtonClick = {
                    localUriHandler.openUri(context.getString(R.string.jar_link, uiState.jar.jarId))
                },
                onCopyClick = {
                    clipboardManager.setText(
                        AnnotatedString(context.getString(R.string.jar_link, uiState.jar.jarId))
                    )
                }
            )
        },
        viewModel = viewModel
    ) { uiState ->
        DetailsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
) {
    var backgroundBoxTopOffset by remember { mutableIntStateOf(0) }

    //Background with rounded top-corners
    Box(modifier = Modifier
        .offset { IntOffset(0, backgroundBoxTopOffset) }
        .fillMaxSize()
        .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
        .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        OwnerJarImage(
            modifier = Modifier
                .size(144.dp)
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    backgroundBoxTopOffset =
                        rect.topCenter.y.toInt() + (rect.bottomCenter.y - rect.topCenter.y).toInt() / 2
                },
            imageUri = Uri.parse(uiState.jar.ownerIcon)
        )

        // TODO: Add jar details content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(60.dp)
                .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TitleJarInfo(uiState = uiState)
                JarCashInfo(uiState = uiState)
                Comment(uiState = uiState)
            }

        }
    }
}

@Composable
private fun TitleJarInfo(modifier: Modifier = Modifier, uiState: DetailsUiState) {
    Box(
        modifier = modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.jar.ownerName,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = uiState.jar.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            IsActiveStatus(uiState = uiState)
            Text(
                text = uiState.jar.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = if (uiState.jar.description.isEmpty()) 0.dp else 20.dp)
                    .then(
                        if (uiState.jar.description.isEmpty()) {
                            Modifier.size(0.dp)
                        } else {
                            Modifier
                        }
                    )

            )
        }
    }
}

@Composable
private fun IsActiveStatus(uiState: DetailsUiState) {
    Row {
        val closed = uiState.jar.closed
        val goal = uiState.jar.goal
        if (!closed) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                    .height(22.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.jar_active),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp
                    )
                )

            }
            if (goal < 1) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(22.dp)
                        .clip(shape = RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.jar_no_goal),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp
                        )
                    )

                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                    .height(22.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.jar_closed),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp
                    )
                )

            }
        }
    }
}

@Composable
private fun JarCashInfo(modifier: Modifier = Modifier, uiState: DetailsUiState) {
    val progress: Float = uiState.jar.amount.toFloat() / uiState.jar.goal.toFloat()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 46.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JarCashBox(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        title = stringResource(id = R.string.jar_accumulated),
                        amount = uiState.jar.amount,
                        currency = uiState.jar.currency
                    )
                    if (uiState.jar.goal > 0) {
                        Spacer(modifier = Modifier.width(if (uiState.jar.goal < 100000000000) 20.dp else 16.dp))
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.width(if (uiState.jar.goal < 100000000000) 20.dp else 16.dp))
                        JarCashBox(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            title = stringResource(id = R.string.jar_goal),
                            amount = uiState.jar.goal,
                            currency = uiState.jar.currency
                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = { if (uiState.jar.closed) 1f else progress },
                strokeCap = StrokeCap.Round,
                trackColor = MaterialTheme.colorScheme.onPrimary,
                color = if (uiState.jar.closed) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(8.dp)
            )
        }

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
            contentDescription = "",
            modifier = modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = modifier
        ) {
            val currencySymbol = getCurrencySymbol(currency)
            val formattedAmount = String.format("%,.2f", amount / 100.0)
            Text(
                text = title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
            )
            Text(
                text = "$formattedAmount $currencySymbol",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }
}

@Composable
private fun Comment(modifier: Modifier = Modifier, uiState: DetailsUiState) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        Column {
            val yourComent = stringResource(R.string.your_comment)
            Text(
                text = "$yourComent:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = uiState.jar.userComment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.jar_comment_edit),
                fontSize = 12.sp,
                color = if (uiState.jar.closed) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { })
        }


    }
}

private fun getCurrencySymbol(numericCode: Int): String? {
    val currency = Currency.getAvailableCurrencies().firstOrNull { it.numericCode == numericCode }
    return currency?.symbol
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    ComposeCampGroup4Theme {

        DetailsScreen(
            uiState = DetailsUiState(
                jar = Jar(
                    jarId = "123",
                    longJarId = "1234567890",
                    amount = 58935366131,
                    goal = 100000000000,
                    ownerIcon = "https://example.com/icon.png",
                    title = "Благодійний фонд Сергія Притули",
                    ownerName = "Благодійний фонд Сергія Притули, БО",
                    currency = 980,
                    description = "опис баночки",
                    closed = false,
                    userComment = "....що ж тут написати, хз хз, але ж треба тестити"
                )
            ),
            onEvent = { }
        )
    }
}