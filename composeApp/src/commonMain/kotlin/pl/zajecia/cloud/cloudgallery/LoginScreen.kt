package pl.zajecia.cloud.cloudgallery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var loginText by remember { mutableStateOf(TextFieldValue("")) }
    var passwordText by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cloud Gallery",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Error display
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Login Field
        OutlinedTextField(
            value = loginText,
            onValueChange = {
                loginText = it
                errorMessage = null  // clear error when user types
            },
            label = { Text("Login") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = passwordText,
            onValueChange = {
                passwordText = it
                errorMessage = null  // clear error when user types
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Log In Button
        Button(
            onClick = {
                if (loginText.text.isBlank() || passwordText.text.isBlank()) {
                    errorMessage = "Please fill out all fields."
                } else {
                    coroutineScope.launch {
                        signInWithEmailAndPassword(
                            email = loginText.text,
                            password = passwordText.text,
                            onResult = { isSuccess, message ->
                                if (isSuccess) {
                                    onLoginSuccess()
                                } else {
                                    errorMessage = message
                                }
                            }
                        )
                    }
                }
            },
            enabled = loginText.text.isNotBlank() && passwordText.text.isNotBlank(),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Log In")
        }
    }
}