package com.example.univoice.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentenceBuilderScreen() {

    var word by remember { mutableStateOf("") }
    val entries = remember { mutableStateListOf<String>() }

    val sentence = entries.joinToString(" ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        TextButton(
            onClick = { entries.clear() }
        ) {
            Text("← Clear and go back")
        }

        Text(
            text = "Sentence Builder",
            fontSize = 26.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Build a sentence by adding words or sentences.")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = word,
            onValueChange = { word = it },
            label = { Text("Enter a word or sentence") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (word.isNotBlank()) {
                    entries.add(word.trim())
                    word = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to Sentence")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Your Entries",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (entries.isEmpty()) {
                    Text(
                        text = "Your entries will appear here.",
                        fontSize = 16.sp
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        itemsIndexed(entries) { index, entry ->
                            Text(
                                text = "${index + 1}. $entry",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Combined Sentence",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = sentence.ifBlank {
                        "Your sentence will appear here."
                    },
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { entries.clear() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Sentence")
                }
            }
        }
    }
}
