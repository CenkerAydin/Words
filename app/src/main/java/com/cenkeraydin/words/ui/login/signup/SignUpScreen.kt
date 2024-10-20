package com.cenkeraydin.words.ui.login.signup

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.R

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    RegisterContent()

}


@Composable
fun RegisterContent() {


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

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
                .fillMaxSize()  // Ekranın tamamını kaplayacak
                .padding(0.dp),  // Tüm padding'i sıfırla
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "Logo",

                modifier = Modifier
                    .padding(50.dp)
                    .size(150.dp)  // Sabit boyut, padding kaldırıldı
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),  // Sabit height yerine ekranı dolduracak
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Email Input Field
                    Text(
                        text = stringResource(id = R.string.email_address),
                        modifier = Modifier.alpha(0.7f),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = stringResource(id = R.string.enter_your_email_address)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password Input Field
                    Text(
                        text = stringResource(id = R.string.password),
                        modifier = Modifier.alpha(0.7f),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = stringResource(id = R.string.enter_your_password)) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    // Confirm Password Input Field
                    Text(
                        text = stringResource(id = R.string.confirm_password),
                        modifier = Modifier.alpha(0.7f),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = stringResource(id = R.string.confirm_password)) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Register Button
                    Button(
                        onClick = { /* Register Action */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Text(text = "Register")
                    }

                    // Sign In Text
                    Text(
                        text = stringResource(id = R.string.already_have_an_account_sign_in),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { /* Navigate to login */ }
                            .alpha(0.7f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val navHostController = rememberNavController()
    SignUpScreen(navHostController)
}