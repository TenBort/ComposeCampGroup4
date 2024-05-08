package com.example.composecampgroup4.presentation.screens.details

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.presentation.core.base.BaseContentLayout
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage
import com.example.composecampgroup4.presentation.core.components.TopBarApp
import com.example.composecampgroup4.presentation.screens.details.components.Comment
import com.example.composecampgroup4.presentation.screens.details.components.DetailsBottomButton
import com.example.composecampgroup4.presentation.screens.details.components.JarCashInfo
import com.example.composecampgroup4.presentation.screens.details.components.TitleJarInfo
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiEvent
import com.example.composecampgroup4.presentation.screens.details.screen_handling.DetailsUiState
import com.example.composecampgroup4.presentation.theme.ComposeCampGroup4Theme
import com.example.composecampgroup4.presentation.core.utils.getCurrencySymbol

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
                },
                enabled = !uiState.jar.closed
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
    val scrollState = rememberScrollState()

    //Background with rounded top-corners
    Box(modifier = Modifier
        .offset { IntOffset(0, backgroundBoxTopOffset) }
        .fillMaxSize()
        .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
        .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        OwnerJarImage(
            modifier = Modifier
                .size(144.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    backgroundBoxTopOffset =
                        rect.topCenter.y.toInt() + (rect.bottomCenter.y - rect.topCenter.y).toInt() / 2
                },
            imageUri = Uri.parse(uiState.jar.ownerIcon)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.verticalScroll(state = scrollState)
        ) {
            if (!uiState.commentEdited) {
                TitleJarInfo(
                    title = uiState.jar.title,
                    ownerName = uiState.jar.ownerName,
                    description = uiState.jar.description,
                    jarClosed = uiState.jar.closed,
                    goal = uiState.jar.goal
                )

                JarCashInfo(
                    amount = uiState.jar.amount,
                    goal = uiState.jar.goal,
                    currency = uiState.jar.currency,
                    jarClosed = uiState.jar.closed
                )
            }

            Comment(uiState = uiState, onEvent = onEvent)
        }
    }
}


@Composable
private fun JarCashInfo(
    modifier: Modifier = Modifier,
    amount: Long,
    goal: Long,
    currency: Int,
    jarClosed: Boolean
) {
    val progress = amount.toFloat() / goal.toFloat()


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
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        title = stringResource(id = R.string.jar_accumulated),
                        amount = amount,
                        currency = currency

                    )
                    if (goal > 0) {
                        Spacer(modifier = Modifier.width(if (goal < 100000000000) 20.dp else 16.dp))
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.width(if (goal < 100000000000) 20.dp else 16.dp))
                        JarCashBox(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            title = stringResource(id = R.string.jar_goal),
                            amount = goal,
                            currency = currency

                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = { if (jarClosed) 1f else progress },
                strokeCap = StrokeCap.Round,
                trackColor = MaterialTheme.colorScheme.onPrimary,
                color = if (jarClosed) MaterialTheme.colorScheme.outline
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
            modifier = modifier.size(24.dp)
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
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Composable
private fun Comment(modifier: Modifier = Modifier, jarClosed: Boolean, comment: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        Column {
            val yourComment = stringResource(R.string.your_comment)
            Text(
                text = "$yourComment:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.jar_comment_edit),
                fontSize = 12.sp,
                color = if (jarClosed) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { })
        }


    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    // uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailsScreenPreview() {
    ComposeCampGroup4Theme {

        DetailsScreen(
            uiState = DetailsUiState(
                jar = Jar(
                    jarId = "123",
                    longJarId = "1234567890",
                    amount = 158935366132,
                    goal = 170000000000,
                    ownerIcon = "https://example.com/icon.png",
                    title = "Благодійний фонд Сергія Притули",
                    ownerName = "Благодійний фонд Сергія Притули, БО",
                    currency = 980,
                    description = "опис баночки",
                    closed = false,
                    userComment = "Притула, дрони"
                )
            ),
            onEvent = { }
        )
    }
}