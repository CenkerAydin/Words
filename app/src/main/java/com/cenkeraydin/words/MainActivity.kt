package com.cenkeraydin.words

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.data.repos.WordRepository
import com.cenkeraydin.words.navigation.Navigation
import com.cenkeraydin.words.ui.WordListScreen
import com.cenkeraydin.words.ui.login.signin.SignInScreen
import com.cenkeraydin.words.ui.theme.WordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // Hilt entegrasyonu için gerekli anotasyon
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Tema uygulaması (Opsiyonel, kendi temanız varsa ekleyebilirsiniz)
            WordsTheme {
                // Navigation composable'ı burada çağrılıyor
                Navigation()
            }
        }
    }
}