package com.cenkeraydin.words.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.data.model.Word

@Composable
fun WordListScreen(navHostController: NavHostController, viewModel: WordViewModel) {

    val wordList by viewModel.words.observeAsState(initial = emptyList())
    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Text(
            text = "Words",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue)
                )
            ),

        )

        Spacer(modifier = Modifier.height(8.dp))

        if (wordList.isEmpty()) {
            Text(text = "No words available", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 72.dp)
            ) {
                items(wordList) { word: Word ->
                    WordItem(word = word) {
                        selectedWord = word
                        showDialog = true
                    }
                }
            }
        }
        if (showDialog && selectedWord != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Add to Learned?") },
                text = { Text(text = "Do you want to add \"${selectedWord?.englishWord}\" to learned words?") },
                confirmButton = {
                    Button(onClick = {
                        viewModel.markAsLearned(selectedWord!!) // Kelimeyi learned listesine ekle
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

@Composable
fun WordItem(word: Word, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp) // Kenar yuvarlama
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = word.englishWord, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = word.turkishWord, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun WordListScreenPreview() {
    val viewModel: WordViewModel = viewModel()
    var navHostController = rememberNavController()
    WordListScreen(navHostController, viewModel)
}


