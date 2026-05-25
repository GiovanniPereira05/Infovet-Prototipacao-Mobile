package com.example.infovet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.graphics.Color

data class Dataset(
    val titulo: String,
    val descricao: String
)

@Composable
fun TelaDestaques() {

    val lista = listOf(
        Dataset("Criptococose no Brasil em 2025", "100 downloads"),
        Dataset("Canine Parvovirus", "120 downloads")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Datasets", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Explore os principais datasets do Brasil na área veterinária",
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tags
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Tag("Gatos")
            Tag("Cachorro")
            Tag("Coelho")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("⭐ Em destaque", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))


    }
}
@Composable
fun DestaqueCard(dataset: Dataset) {
    Card(
        modifier = Modifier.width(260.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = dataset.titulo,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = dataset.descricao,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}
