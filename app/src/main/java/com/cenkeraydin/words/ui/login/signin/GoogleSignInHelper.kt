package com.cenkeraydin.words.ui.login.signin

import com.cenkeraydin.words.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper(private val context: Context) {

    // FirebaseAuth instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Configure Google Sign-In and the GoogleSignInClient
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id)) // Firebase Console'dan alınan istemci kimliğini buraya koy
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    // Sign-in intenti başlatır
    fun signIn(launcher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>, onComplete: (Boolean, String?) -> Unit) {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d("GoogleSignInHelper", "Google Sign-In successful with account: ${account?.email}")
            firebaseAuthWithGoogle(account?.idToken, onComplete)
        } catch (e: ApiException) {
            Log.e("GoogleSignInHelper", "Google Sign-In failed", e)
            onComplete(false, "Google Sign-In failed: ${e.message}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?, onComplete: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleSignInHelper", "Firebase Authentication successful.")
                    onComplete(true, null)
                } else {
                    Log.e("GoogleSignInHelper", "Firebase Authentication failed", task.exception)
                    onComplete(false, "Firebase Authentication failed: ${task.exception?.message}")
                }
            }
    }
}
