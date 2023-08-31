package com.anilyilmaz.nativefirebase.feature.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anilyilmaz.nativefirebase.feature.list.ListRoute
import com.anilyilmaz.nativefirebase.feature.profile.ProfileRoute
import com.anilyilmaz.nativefirebase.feature.signin.SignInRoute
import com.anilyilmaz.nativefirebase.feature.signup.SignUpRoute
import com.anilyilmaz.nativefirebase.feature.welcome.WelcomeRoute

@Composable
fun NativeFirebaseApp() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NativeFirebaseNavHost(
            navController = navController
        )
    }
}

@Composable
private fun NativeFirebaseNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "signIn") {
        composable(route = "signIn") {
            SignInRoute(
                onLoggedIn = {
                    navController.navigate("list") {
                        launchSingleTop = true
                        popUpTo("signIn") { inclusive = true }
                    }
                },
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
                onLoggedIn = {
                    navController.navigate("list") {
                        launchSingleTop = true
                        popUpTo("signIn") { inclusive = true }
                    }
                },
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
            WelcomeRoute(
                onListClick = {
                    navController.navigate("list") {
                        launchSingleTop = true
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        composable(route = "list") {
            ListRoute(
                onProfileClick = { navController.navigate("profile") }
            )
        }
        composable(route = "profile") {
            ProfileRoute(
                onBackClick = { navController.popBackStack() },
                onLogOutClick = {
                    navController.navigate("signIn") {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}