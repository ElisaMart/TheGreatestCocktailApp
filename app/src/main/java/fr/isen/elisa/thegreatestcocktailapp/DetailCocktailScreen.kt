package fr.isen.elisa.thegreatestcocktailapp

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.isen.elisa.thegreatestcocktailapp.data.FavoritesManager
import fr.isen.elisa.thegreatestcocktailapp.model.Drink
import fr.isen.elisa.thegreatestcocktailapp.network.NetworkManager
import fr.isen.elisa.thegreatestcocktailapp.ui.theme.OrangeMain

@Composable
fun DetailCocktailScreen(
    idDrink: String
) {
    val context = LocalContext.current

    var drink by remember { mutableStateOf<Drink?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFavorite by remember(idDrink) {
        mutableStateOf(FavoritesManager.isFavorite(context, idDrink))
    }

    LaunchedEffect(idDrink) {
        isLoading = true
        errorMessage = null

        try {
            drink = NetworkManager.api.getCocktailById(idDrink).drinks?.firstOrNull()
            if (drink == null) {
                errorMessage = "Cocktail introuvable"
            }
        } catch (e: Exception) {
            errorMessage = "Erreur de chargement : ${e.message}"
        } finally {
            isLoading = false
        }
    }

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

        drink != null -> {
            val currentDrink = drink!!

            Column(modifier = Modifier.fillMaxSize()) {
                AppHeader(title = currentDrink.strDrink ?: "Cocktail")

                MainCreamCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            AsyncImage(
                                model = currentDrink.strDrinkThumb,
                                contentDescription = currentDrink.strDrink,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = currentDrink.strDrink ?: "Cocktail",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = "ID : ${currentDrink.idDrink}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    currentDrink.strCategory?.let {
                                        Text(
                                            text = "Catégorie : $it",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    currentDrink.strAlcoholic?.let {
                                        Text(
                                            text = "Type : $it",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    currentDrink.strGlass?.let {
                                        Text(
                                            text = "Verre : $it",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        FavoritesManager.toggleFavorite(context, currentDrink.idDrink)
                                        isFavorite = FavoritesManager.isFavorite(context, currentDrink.idDrink)
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        contentDescription = "Favori",
                                        tint = OrangeMain,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                text = "Ingrédients",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        currentDrink.ingredientsList().forEach { ingredient ->
                            item {
                                Text(
                                    text = "• $ingredient",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Instructions",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentDrink.strInstructions ?: "Aucune instruction disponible",
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