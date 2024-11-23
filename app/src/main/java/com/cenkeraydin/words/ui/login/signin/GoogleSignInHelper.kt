package com.cenkeraydin.words.ui.login.signin

import android.content.Context
import com.cenkeraydin.words.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class GoogleSignInHelper(context: Context) {

    private val tag = "GoogleSignInHelper"
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signIn(signInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }
    suspend fun handleSignInResult(account: GoogleSignInAccount?): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user != null
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "handleSignInResult error: " + e.message)
            false
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut()
    }
}
