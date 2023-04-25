package co.kotekote.partyverse.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import co.kotekote.partyverse.R
import co.kotekote.partyverse.data.supabase.rememberSupabaseClient
import co.kotekote.partyverse.ui.navigation.NavActions
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

enum class ErrorState {
    NONE,
    CONNECTION,
    LOGIN_CREDENTIALS,
    SIGNUP_CREDENTIALS,
    UNKNOWN
}

@Composable
fun LoginScreen(navActions: NavActions) {
    val supabaseClient = rememberSupabaseClient()
    val coroutineScope = rememberCoroutineScope()
    val sessionStatus = supabaseClient.gotrue.sessionStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (error, setError) = remember { mutableStateOf(ErrorState.NONE) }
    val (showPassword, setShowPassword) = remember { mutableStateOf(false) }

    val areFieldsValid = remember(email, password) {
        email.isNotEmpty() && password.isNotEmpty()
    }

    val signupSuccessMessage = stringResource(R.string.signup_success)

    val authActionHandler = { credentialsError: ErrorState, action: suspend () -> Unit ->
        setError(ErrorState.NONE)
        coroutineScope.launch {
            try {
                action()
            } catch (ex: Exception) {
                Log.e("LoginScreen", "auth error", ex)
                setError(
                    when (ex) {
                        is BadRequestRestException, is UnauthorizedRestException -> credentialsError
                        is HttpRequestException, is HttpRequestTimeoutException -> ErrorState.CONNECTION
                        else -> ErrorState.UNKNOWN
                    }
                )
            }
        }
    }

    LaunchedEffect(sessionStatus.value) {
        if (sessionStatus.value is SessionStatus.Authenticated) {
            navActions.navigateHome()
        }
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp, 0.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        OutlinedTextField(
            email,
            setEmail,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.email_field)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            password,
            setPassword,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.password_field)) },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                IconButton(onClick = { setShowPassword(!showPassword) }) {
                    Icon(
                        if (showPassword) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        stringResource(
                            if (showPassword) R.string.hide_password_button
                            else R.string.show_password_button
                        ),
                    )
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    authActionHandler(ErrorState.LOGIN_CREDENTIALS) {
                        supabaseClient.gotrue.loginWith(Email) {
                            this.email = email
                            this.password = password
                        }
                        navActions.navigateHome()
                    }
                },
                enabled = areFieldsValid,
                modifier = Modifier.weight(1F)
            ) {
                Text(stringResource(R.string.login_button))
            }

            Button(
                onClick = {
                    authActionHandler(ErrorState.SIGNUP_CREDENTIALS) {
                        supabaseClient.gotrue.signUpWith(Email) {
                            this.email = email
                            this.password = password
                        }
                        /* TODO / WARNING
                            Supabase still returns success even if email is taken to
                            prevent user enumeration. We'll need to find a better way to
                            communicate this.
                        */
                        snackbarHostState.showSnackbar(signupSuccessMessage)
                    }
                },
                enabled = areFieldsValid,
                modifier = Modifier.weight(1F)
            ) {
                Text(stringResource(R.string.signup_button))
            }
        }
        if (error != ErrorState.NONE) {
            Text(
                when (error) {
                    ErrorState.CONNECTION -> stringResource(R.string.error_connection)
                    ErrorState.LOGIN_CREDENTIALS -> stringResource(R.string.error_login_credentials)
                    ErrorState.SIGNUP_CREDENTIALS -> stringResource(R.string.error_signup_credentials)
                    else -> stringResource(R.string.error_unknown)
                }, color = MaterialTheme.colors.error
            )
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}
