package com.cenkeraydin.words.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.navigation.NavHostController
import com.cenkeraydin.words.WordViewModel
import com.cenkeraydin.words.data.model.Word
import com.cenkeraydin.words.ui.login.signup.SignUpViewModel
import com.cenkeraydin.words.util.GradientText

@Composable
fun WordListScreen(
    navHostController: NavHostController,
    viewModel: WordViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val wordList by viewModel.words.observeAsState(initial = emptyList())
    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GradientText("Words")
            IconButton(onClick = {
                signUpViewModel.signOut()
                navHostController.navigate("signInScreen") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Sign Out",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search words") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true
        )
        val filteredWordList = wordList.filter {
            it.englishWord.contains(searchQuery, ignoreCase = true) ||
                    it.turkishWord.contains(searchQuery, ignoreCase = true)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredWordList.isEmpty()) {
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
                items(filteredWordList) { word: Word ->
                    WordItem(
                        word = word,
                        onClick = {
                            selectedWord = word
                            showDialog = true
                        },
                        onDeleteClick = {
                            viewModel.deleteWord(word) // Call a function in your ViewModel to delete the word
                        }
                    )
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
fun WordItem(word: Word, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = word.englishWord,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = word.turkishWord,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}





