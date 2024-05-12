package com.example.composecampgroup4.presentation.screens.home.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecampgroup4.R
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.presentation.core.components.ActiveStatus
import com.example.composecampgroup4.presentation.core.components.OwnerJarImage

@Composable
fun JarItemInfo(
    modifier: Modifier = Modifier,
    jar: Jar,
    onFavoriteClick: (Jar) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OwnerJarImage(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterVertically),
            imageUri = Uri.parse(jar.ownerIcon),
            isClosed = jar.closed
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            // JarTitle
            Text(
                text = jar.title,
                modifier = Modifier
                    .wrapContentSize(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 20.sp
            )

            // JarOwnerName
            Text(
                text = stringResource(R.string.collected_by_owner_name, jar.ownerName),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 6.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 18.sp
            )

            ActiveStatus(
                modifier = Modifier.padding(top = 12.dp),
                jarClosed = jar.closed,
                hasNoGoal = jar.goal == 0L
            )
        }

        JarItemIcons(
            modifier = Modifier.align(Alignment.Top),
            jar = jar,
            onFavoriteClick = onFavoriteClick
        )
    }
}