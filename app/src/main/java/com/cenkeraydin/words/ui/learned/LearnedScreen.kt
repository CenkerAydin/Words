package com.cenkeraydin.words.ui.learned

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.ui.home.WordItem

@Composable
fun LearnedScreen(viewModel: WordViewModel = hiltViewModel()) {
    val learnedList by viewModel.learnedWords.observeAsState(initial = emptyList())

    var selectedLearnedWord by remember { mutableStateOf<Word?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            text = "Learned Words",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue)
                )
            ),

            )

        if (learnedList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No words available",
                    fontWeight = FontWeight.Bold,

                    )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 72.dp
                )
            ) {
                items(learnedList) { word: Word ->
                    WordItem(
                        word = word,
                        onClick = {
                            selectedLearnedWord = word
                            showDialog = true
                        },
                        onDeleteClick = {
                            viewModel.deleteWord(word) // Call a function in your ViewModel to delete the word
                        }
                    )
                }
            }
        }

        if (showDialog && selectedLearnedWord != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Mark as Unlearned?") },
                text = { Text(text = "Do you want to mark \"${selectedLearnedWord?.englishWord}\" as unlearned?") },
                confirmButton = {
                    Button(onClick = {
                        viewModel.markAsUnlearned(selectedLearnedWord!!)
                        showDialog = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}
