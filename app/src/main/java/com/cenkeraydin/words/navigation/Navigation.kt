package com.cenkeraydin.words.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.ui.add.AddScreen
import com.cenkeraydin.words.ui.learned.LearnedScreen
import com.cenkeraydin.words.ui.profile.ProfileScreen
import com.cenkeraydin.words.ui.home.WordListScreen
import com.cenkeraydin.words.ui.login.signin.SignInScreen
import com.cenkeraydin.words.ui.login.signup.SignUpScreen


@Composable
fun Navigation(navController : NavHostController) {
    val viewModel : WordViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "signInScreen"  ) {
        composable(BottomNavItem.Home.route) {
            WordListScreen(navController, viewModel)
        }
        composable(BottomNavItem.Learned.route) {
           LearnedScreen(viewModel)
        }
        composable(BottomNavItem.Add.route) {
           AddScreen(viewModel)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
        composable("signInScreen") {
            SignInScreen(navController)
        }
        composable("signUpScreen") {
            SignUpScreen(navController)
        }
    }
}

