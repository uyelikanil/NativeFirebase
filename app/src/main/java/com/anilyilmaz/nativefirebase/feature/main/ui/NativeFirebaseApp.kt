package com.anilyilmaz.nativefirebase.feature.main.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anilyilmaz.nativefirebase.feature.signin.SignInRoute
import com.anilyilmaz.nativefirebase.feature.signup.SignUpRoute
import com.anilyilmaz.nativefirebase.feature.welcome.WelcomeRoute

@Composable
fun NativeFirebaseApp() {
    val navController = rememberNavController()

    NativeFirebaseNavHost(
        navController = navController
    )
}

@Composable
private fun NativeFirebaseNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "signIn") {
        composable(route = "signIn") {
            SignInRoute(
                onSuccess = {
                    navController.navigate("welcome") {
                        launchSingleTop = true
                        popUpTo("signIn") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signUp") {
                        launchSingleTop = true
                        popUpTo("signIn") { inclusive = true }
                    }
                }
            )
        }
        composable(route = "signUp") {
            SignUpRoute(
                onSuccess = {
                    navController.navigate("welcome") {
                        launchSingleTop = true
                        popUpTo("signUp") { inclusive = true }
                    }
                },
                onSignInClick = {
                    navController.navigate("signIn") {
                        launchSingleTop = true
                        popUpTo("signUp") { inclusive = true }
                    }
                }
            )
        }
        composable(route = "welcome") {
            WelcomeRoute()
        }
    }
}