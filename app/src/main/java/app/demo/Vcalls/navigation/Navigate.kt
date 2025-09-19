package app.demo.Vcalls.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.demo.Vcalls.view.auth.LoginScreen
import app.demo.Vcalls.view.auth.SignUpScreen
import app.demo.Vcalls.view.home.HomeScreen
import app.demo.Vcalls.view.splash.SplashScreen

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignUpScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
    }
}


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
}