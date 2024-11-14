package com.cenkeraydin.words.ui.login.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cenkeraydin.words.R

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val signInState by viewModel.signInState.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF8551),
                        Color(0xFFFF609A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),  // Tüm padding'i sıfırla
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo or Image
            Image(
                painter = painterResource(id = R.drawable.login_word),
                contentDescription = "App Logo",
                modifier = Modifier
                    .height(150.dp)
                    .padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(28.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Welcome Back!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Please login with your information",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Email Address",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter your email address") },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.mail),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Email Icon"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Password",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        label = { Text("Enter your password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = { password = it },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.password),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Email Icon"
                            )
                        },
                        trailingIcon = {
                            val icon =
                                if (isPasswordVisible) R.drawable.baseline_visibility_on else R.drawable.baseline_visibility_off
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = "Visibility Icon"
                                )
                            }
                        },
                    )

                    Button(
                        onClick = { viewModel.signIn(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(text = "Login", fontSize = 16.sp)
                    }
                    errorMessage?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Text(
                        text = "Don't have an account? Sign Up!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, top = 16.dp)
                            .clickable{
                                navHostController.navigate("signUpScreen")
                            },
                        color = colorResource(id = R.color.purple_500),
                        fontWeight = FontWeight.Bold
                    )


                    GoogleSignInButton(navHostController = navHostController)
                }
            }
        }
    }
    if (signInState == true) {
        LaunchedEffect(Unit) {
            navHostController.navigate("home") {
                popUpTo("signInScreen") { inclusive = true }
            }
        }
    }
}

