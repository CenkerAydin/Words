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
import com.cenkeraydin.words.ui.login.signin.SignInViewModel
import com.cenkeraydin.words.ui.login.signup.SignUpScreen
import com.cenkeraydin.words.ui.login.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Navigation(navController : NavHostController) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val isUserLoggedIn = firebaseAuth.currentUser != null

    NavHost(
        navController = navController,
        startDestination = if(isUserLoggedIn) "home" else "signUpScreen"  ) {
        composable(BottomNavItem.Home.route) {
            WordListScreen(navController)
        }
        composable(BottomNavItem.Learned.route) {
           LearnedScreen()
        }
        composable(BottomNavItem.Add.route) {
           AddScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
        composable("signInScreen") {
            SignInScreen(navController,)
        }
        composable("signUpScreen") {
            SignUpScreen(navController )
        }
    }
}

