package com.example.infovet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color

data class Download(
    val titulo: String,
    val descricao: String
)
@Composable
fun TelaDownloads() {

    val lista = listOf(
        Download("Dataset Bovinos", "Dados sobre saúde de bovinos"),
        Download("Dataset Caninos", "Doenças comuns em cães")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Downloads",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explore e baixe datasets veterinários",
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(lista) { item ->
                DownloadItem(item)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
@Composable
fun DownloadItem(download: Download) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = download.titulo,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = download.descricao,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

        }
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text("Download")
        }
    }
}