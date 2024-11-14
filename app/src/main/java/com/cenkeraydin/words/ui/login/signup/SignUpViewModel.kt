package com.cenkeraydin.words.ui.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _signUpResult = MutableLiveData<Result<String>>()
    val signUpResult: LiveData<Result<String>> = _signUpResult

    fun signUp(email: String, password: String, confirmPassword: String) {

        if (email.isEmpty() || password.isEmpty() || password != confirmPassword) {
            _signUpResult.value = Result.failure(Exception("Please fill all fields correctly"))
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userId = user?.uid
                    val userMap = hashMapOf(
                        "email" to email,
                        "password" to password,
                        "userId" to userId
                    )
                    user?.let {
                        firestore.collection("users")
                            .document(userId!!)
                            .set(userMap)
                            .addOnSuccessListener {
                                _signUpResult.value =
                                    Result.success("Sign up successful and user data saved to Firestore")
                            }
                            .addOnFailureListener { exception ->
                                Log.e("SignUp", "Failed to save user data to Firestore", exception)
                                _signUpResult.value = Result.failure(exception)
                            }
                    }
                }
                 else {
                    Log.e("SignUp", "Sign up failed", task.exception)
                    _signUpResult.value = Result.failure(task.exception ?: Exception("Unknown error"))
                }
            }
            .addOnFailureListener { exception ->
                Log.e("SignUp", "Sign up failed", exception)
                _signUpResult.value = Result.failure(exception)
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}
