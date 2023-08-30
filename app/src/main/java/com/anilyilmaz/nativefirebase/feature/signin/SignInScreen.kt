package com.anilyilmaz.nativefirebase.feature.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import com.anilyilmaz.nativefirebase.feature.signup.SignUpUiState
import com.anilyilmaz.nativefirebase.feature.signup.SignUpUserState
import com.anilyilmaz.nativefirebase.feature.signup.SignUpViewModel

@Composable
fun SignInRoute(viewModel: SignInViewModel = hiltViewModel(),
                onSuccess: () -> Unit,
                onSignUpClick: () -> Unit) {
    if(viewModel.hasUser()) {
        onSuccess()
        return
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userState by viewModel.userState.collectAsStateWithLifecycle()

    SignInScreen(
        uiState,
        userState,
        viewModel::updateEmail,
        viewModel::updatePassword,
        viewModel::onSignInClick,
        viewModel::errorHandled,
        onSuccess,
        onSignUpClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInScreen(
    uiState: SignInUiState,
    userState: SignInUserState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onSignInClick: () -> Unit,
    errorHandled: () -> Unit,
    onSuccess: () -> Unit,
    onSignUpClick: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        text = stringResource(id = R.string.brand_name)
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    titleContentColor = Color.Gray
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .safeContentPadding()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.sign_in),
                fontSize = 30.sp)

            SignInLayout(userState, updateEmail, updatePassword)

            when(uiState) {
                is SignInUiState.SignIn -> {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = { onSignInClick() }) {
                        Text(stringResource(id = R.string.sign_in))
                    }
                }

                is SignInUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                        .padding(top = 10.dp)
                    )
                }

                is SignInUiState.Success -> {
                    onSuccess()
                }

                is SignInUiState.Error -> {
                    if(uiState.messageId != 0) {
                        val message: String = stringResource(id = uiState.messageId)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    errorHandled()
                }
            }
        }

        TextButton(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .wrapContentSize(Alignment.BottomEnd),
            onClick = { onSignUpClick() }) {
            Text(stringResource(id = R.string.sign_up))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInLayout(userState: SignInUserState,
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
fun SignInScreenPreview() {
    NativeFirebaseTheme {
        SignInScreen(
            SignInUiState.Loading,
            SignInUserState(),
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}