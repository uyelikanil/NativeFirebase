package com.anilyilmaz.nativefirebase.feature.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anilyilmaz.nativefirebase.R

@Composable
fun WelcomeRoute() {
    WelcomeScreen()
}

@Composable
private fun WelcomeScreen() {
    Surface {
        Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                text = stringResource(id = R.string.welcome),
                fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    WelcomeScreen()
}