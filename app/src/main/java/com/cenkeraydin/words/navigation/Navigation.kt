package com.cenkeraydin.words.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.ui.WordListScreen
import com.cenkeraydin.words.ui.login.signin.SignInScreen
import com.cenkeraydin.words.ui.login.signup.SignUpScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel : WordViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "signInScreen"
    ) {
        composable("wordListScreen") {
            WordListScreen(navController, viewModel)
        }
        composable("signInScreen") {
            SignInScreen(navController)
        }
        composable("signUpScreen") {
            SignUpScreen(navController)
        }
    }
}