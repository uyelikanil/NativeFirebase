package com.anilyilmaz.nativefirebase.feature.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anilyilmaz.nativefirebase.R
import com.anilyilmaz.nativefirebase.core.designsystem.theme.NativeFirebaseTheme

@Composable
fun SignUpRoute(viewModel: SignUpViewModel = hiltViewModel(),
                onLoggedIn: () -> Unit,
                onSuccess: () -> Unit,
                onSignInClick: () -> Unit) {
    if(viewModel.hasUser) {
        onLoggedIn()
        return
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userState by viewModel.userState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState,
        userState,
        viewModel::updateEmail,
        viewModel::updatePassword,
        viewModel::onSignUpClick,
        viewModel::errorHandled,
        onSuccess,
        onSignInClick)
}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    userState: SignUpUserState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onSignUpClick: () -> Unit,
    errorHandled: () -> Unit,
    onSuccess: () -> Unit,
    onSignInClick: () -> Unit) {
    val context = LocalContext.current

    Box(modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .statusBarsPadding(),
                text = stringResource(id = R.string.sign_up),
                fontSize = 30.sp)

            SignUpLayout(userState, updateEmail, updatePassword)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .wrapContentSize(Alignment.Center)
        ) {
            when(uiState) {
                is SignUpUiState.SignUp -> {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { onSignUpClick() }) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.sign_up),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is SignUpUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                    )
                }

                is SignUpUiState.Success -> {
                    onSuccess()
                }

                is SignUpUiState.Error -> {
                    if(uiState.messageId != 0) {
                        val message: String = stringResource(id = uiState.messageId)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    errorHandled()
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.have_an_account),
                    color = Color.Gray
                )

                TextButton(
                    onClick = { onSignInClick() }) {
                    Text(stringResource(id = R.string.sign_in))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpLayout(userState: SignUpUserState,
                         updateEmail: (String) -> Unit,
                         updatePassword: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    val passwordVisibilityIcon = if(passwordVisibility)
        Icons.Filled.Visibility
    else
        Icons.Filled.VisibilityOff

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        value = userState.email,
        onValueChange = { updateEmail(it) },
        label = { Text(stringResource(id = R.string.email)) },
        placeholder = { Text( stringResource(id = R.string.your_email_address)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions ( onDone = { focusManager.moveFocus(
            focusDirection = FocusDirection.Next,
        ) })
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        value = userState.password,
        onValueChange = { updatePassword(it) },
        label = { Text(stringResource(id = R.string.password)) },
        placeholder = { Text(stringResource(id = R.string.your_password)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions (onDone = { focusManager.clearFocus() }),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(imageVector = passwordVisibilityIcon,
                    contentDescription =  "Password Visibility Icon")
            }
        },
        visualTransformation = if(passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    NativeFirebaseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SignUpScreen(
                SignUpUiState.Loading,
                SignUpUserState(),
                {}, {}, {}, {}, {}, {}
            )
        }
    }
}