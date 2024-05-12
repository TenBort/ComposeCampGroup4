package com.example.composecampgroup4.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.JarProgressBar
import com.example.composecampgroup4.presentation.screens.home.screen_handling.HomeUiEvent

@Composable
fun JarItem(
    modifier: Modifier = Modifier,
    jar: Jar,
    onEvent: (HomeUiEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onEvent(HomeUiEvent.OnJarClicked(jar.jarId)) }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            JarItemInfo(
                jar = jar,
                onFavoriteClick = { onEvent(HomeUiEvent.JarFavouriteChanged(jar)) }
            )

            JarProgressBar(
                modifier = Modifier.padding(vertical = 16.dp),
                goal = jar.goal,
                amount = jar.amount,
                isClosed = jar.closed
            )

            JarItemExpandableSection(
                modifier = Modifier.fillMaxWidth(),
                jar = jar,
                isClosed = jar.closed,
            )
        }
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

    JarItem(jar = jar.value) {}
}