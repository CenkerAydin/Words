package com.cenkeraydin.words.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
) :ViewModel(){

    private val _signInState = MutableLiveData<Boolean>()
    val signInState: LiveData<Boolean> get() = _signInState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun signIn(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _signInState.value = false
            _errorMessage.value = "Please fill all fields correctly"
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signInState.value = true  // Giriş başarılı
                    _errorMessage.value = null
                } else {
                    _signInState.value = false // Giriş başarısız
                    _errorMessage.value = task.exception?.message
                }
            }
    }
}