package fr.isen.elisa.thegreatestcocktailapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.isen.elisa.thegreatestcocktailapp.network.RetrofitInstance

@Composable
fun RandomCocktailScreen() {
    var idDrink by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            idDrink = RetrofitInstance.api.getRandomCocktail().drinks?.firstOrNull()?.idDrink
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

        idDrink != null -> {
            DetailCocktailScreen(idDrink = idDrink!!)
        }
    }
}