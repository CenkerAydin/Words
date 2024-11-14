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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cenkeraydin.words.R
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope() // Get the coroutine scope
    val signUpResult by signUpViewModel.signUpResult.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it.isSuccess) {
                navHostController.navigate("Home"){
                    popUpTo("signUpScreen"){
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, it.exceptionOrNull()?.message ?: "Sign up failed", Toast.LENGTH_LONG).show()
            }
        }
    }

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
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "Logo",

                modifier = Modifier
                    .padding(50.dp)
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
                        modifier = Modifier.fillMaxWidth()
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
                        visualTransformation = PasswordVisualTransformation()
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
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                signUpViewModel.signUp(email, password,confirmPassword)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Text(text = "Register")
                    }

                    OutlinedButton(
                        onClick = {
                            //       authViewModel.signInWithGoogle()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Sign in with Google", color = Color.Black)
                    }

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


