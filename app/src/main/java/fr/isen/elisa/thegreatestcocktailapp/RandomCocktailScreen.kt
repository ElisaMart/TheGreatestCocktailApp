package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager

@Composable
fun RandomCocktailScreen() {
    var idDrink by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        try {
            idDrink = NetworkManager.api.getRandomCocktail().drinks?.firstOrNull()?.idDrink
            if (idDrink == null) {
                errorMessage = "Aucun cocktail trouvé"
            }
        } catch (e: Exception) {
            errorMessage = "Erreur de chargement : ${e.message}"
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        errorMessage != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage ?: "Erreur inconnue",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        idDrink != null -> {
            DetailCocktailScreen(idDrink = idDrink!!)
        }
    }
}