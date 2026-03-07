package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.elisa.thegreatestcocktailapp.network.RetrofitInstance

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit
) {
    var categories by remember { mutableStateOf(listOf<String>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            categories = RetrofitInstance.api.getCategories().drinks?.map { it.strCategory } ?: emptyList()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                ) {
                    Text(
                        text = category,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}