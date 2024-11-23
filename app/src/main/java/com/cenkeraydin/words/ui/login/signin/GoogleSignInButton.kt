package com.cenkeraydin.words.ui.login.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cenkeraydin.words.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInButton(navHostController: NavHostController) {

    val context = LocalContext.current
    val googleSignInHelper = remember { GoogleSignInHelper(context) }
    val coroutineScope = rememberCoroutineScope()

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        coroutineScope.launch {
            try {
                val account = task.getResult(ApiException::class.java)
                val signInSuccessful = googleSignInHelper.handleSignInResult(account)
                if (signInSuccessful) {
                    navHostController.navigate("home") {
                        popUpTo("signInScreen") { inclusive = true }
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }


    OutlinedButton(
        onClick = {
            googleSignInHelper.signIn(signInLauncher)
        },
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google logo",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Sign in with Google", color = Color.Black)
    }

}
