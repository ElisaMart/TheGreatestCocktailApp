package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit
) {
    var categories by remember { mutableStateOf(listOf<String>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        try {
            val response = NetworkManager.api.getCategories()
            categories = response.drinks?.mapNotNull { it.strCategory } ?: emptyList()
            if (categories.isEmpty()) {
                errorMessage = "Aucune catégorie trouvée"
            }
        } catch (e: Exception) {
            errorMessage = "Erreur de chargement : ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader(title = "Catégories")

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MainCreamCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "Erreur inconnue",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            else -> {
                MainCreamCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        items(categories) { category ->
                            MainCreamCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCategoryClick(category) },
                                contentPadding = PaddingValues(14.dp)
                            ) {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}