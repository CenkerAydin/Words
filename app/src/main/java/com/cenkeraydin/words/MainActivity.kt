package com.cenkeraydin.words

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.navigation.Navigation
import com.cenkeraydin.words.ui.BottomNavigationBar
import com.cenkeraydin.words.ui.theme.WordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WordsTheme {
                val navController = rememberNavController() // NavHostController oluşturuluyor
                MainScreen(navController)
            }
        }
    }
}



@Composable
fun MainScreen(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "signInScreen" && currentRoute != "signUpScreen") {
                BottomNavigationBar(navController)
            }
        }
    ) {
        Navigation(navController)
    }
}



