package com.anilyilmaz.nativefirebase.feature.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.designsystem.theme.NativeFirebaseTheme

@Composable
fun WelcomeRoute(onListClick: () -> Unit) {
    WelcomeScreen(onListClick)
}

@Composable
private fun WelcomeScreen(onListClick: () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .wrapContentSize(Alignment.TopCenter),
            text = stringResource(id = R.string.welcome),
            fontSize = 30.sp)

        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            text = stringResource(id = R.string.hi_there_nice_to_see_you),
            color = Color.Gray,
            fontSize = 30.sp)

        Button(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .wrapContentSize(Alignment.BottomCenter),
            onClick = { onListClick() }) {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = stringResource(id = R.string.list),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    NativeFirebaseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WelcomeScreen({})
        }
    }
}