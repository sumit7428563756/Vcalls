package app.demo.Vcalls.view.splash

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.demo.Vcalls.navigation.Screen
import app.demo.Vcalls.sharedPref.UserPreferences
import app.demo.Vcalls.ui.theme.PrimaryDark
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    LaunchedEffect(Unit) {
        delay(1500)
        val token = prefs.getToken()
        if (!token.isNullOrEmpty()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    // UI (loading screen)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = PrimaryDark)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Checking session...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
