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
        startDestination = "welcome") {
        composable(route = "welcome") {
            WelcomeRoute()
        }
    }
}