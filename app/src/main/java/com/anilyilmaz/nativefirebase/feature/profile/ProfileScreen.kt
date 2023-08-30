package com.anilyilmaz.nativefirebase.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.designsystem.theme.NativeFirebaseTheme

@Composable
fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel(), onBackClick: () -> Unit,
                 onLogOutClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = ProfileUiState())

    ProfileScreen(uiState, viewModel::onLogOutClick, onBackClick, onLogOutClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(uiState: ProfileUiState,
                          onLogOut: () -> Unit,
                          onBackClick: () -> Unit,
                          onLogOutClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.profile)
            )
        },
        navigationIcon = {
            TextButton(onClick = { onBackClick() }) {
                Text(
                    text = stringResource(R.string.back),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )
            }
        }
    )

    Box(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            text = uiState.email,
            color = Color.Gray,
            fontSize = 30.sp)

        Button(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .wrapContentSize(Alignment.BottomCenter),
            onClick = {
                onLogOut()
                onLogOutClick()
            }
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = stringResource(id = R.string.log_out),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    NativeFirebaseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreen(
                ProfileUiState("anilyilmaz@gmail.com"),
                {}, {}, {})
        }
    }
}