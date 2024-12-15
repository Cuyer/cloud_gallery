package pl.zajecia.cloud.cloudgallery

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn()) Destination.Home else Destination.Login
        ) {
            composable<Destination.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Destination.Home) {
                            popUpTo(Destination.Login) { inclusive = true }
                        }
                    }
                )
            }
            composable<Destination.Home> {
                HomeScreen()
            }
        }
    }
}

sealed class Destination() {
    @Serializable
    data object Login : Destination()

    @Serializable
    data object Home : Destination()
}