package com.cenkeraydin.words.ui.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.ui.anim.LottieAnimationView

@Composable
fun AddScreen(viewModel: WordViewModel) {
    var englishWord by remember { mutableStateOf("") }
    var turkishWord by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimationView()
        OutlinedTextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            label = { Text("English Word") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = turkishWord,
            onValueChange = { turkishWord = it },
            label = { Text("Turkish Word") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (englishWord.isNotEmpty() && turkishWord.isNotEmpty()) {
                    viewModel.addWord(Word(0,englishWord, turkishWord))
                    showConfirmation = true
                    englishWord = ""
                    turkishWord = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Word")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showConfirmation) {
            Text(
                text = "Word added successfully!",
                color = Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
