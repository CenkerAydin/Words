package com.cenkeraydin.words.ui.login.signup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.R
import com.cenkeraydin.words.ui.login.signin.GoogleSignInButton
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val signUpResult by signUpViewModel.signUpResult.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it.isSuccess) {
                navHostController.navigate("Home") {
                    popUpTo("signUpScreen") {
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    it.exceptionOrNull()?.message ?: "Sign up failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
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
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(top = 20.dp,bottom = 5.dp)
                        .size(150.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.welcome),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(id = R.string.please_register_with_your_information),
                            modifier = Modifier.alpha(0.7f)
                        )


                        Text(
                            text = stringResource(id = R.string.email_address),
                            modifier = Modifier.alpha(0.7f),
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = stringResource(id = R.string.enter_your_email_address)) },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.mail),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Email Icon"
                                )
                            },
                        )

                        Text(
                            text = stringResource(id = R.string.password),
                            modifier = Modifier.alpha(0.7f),
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = stringResource(id = R.string.enter_your_password)) },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.password),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Password Icon"
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

                        Text(
                            text = stringResource(id = R.string.confirm_password),
                            modifier = Modifier.alpha(0.7f),
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text(text = stringResource(id = R.string.confirm_password)) },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.password),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Password Icon"
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
                            onClick = {
                                coroutineScope.launch {
                                    signUpViewModel.signUp(email, password, confirmPassword)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(text = "Register")
                        }

                        GoogleSignInButton(navHostController)

                        Text(
                            text = stringResource(id = R.string.already_have_an_account_sign_in),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable { navHostController.navigate("signInScreen") }
                                .alpha(0.7f),
                            color = colorResource(id = R.color.purple_500),
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    val navHostController = rememberNavController()
    SignUpScreen(navHostController)
}

