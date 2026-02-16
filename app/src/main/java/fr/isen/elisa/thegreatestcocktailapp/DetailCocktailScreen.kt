package fr.isen.elisa.thegreatestcocktailapp
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import fr.isen.elisa.thegreatestcocktailapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cocktail details") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // IMAGE placeholder (partie 4: Coil)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cocktail_mojito_royal_1),
                    contentDescription = "Cocktail image",
                    modifier = Modifier.clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Mojito",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Categories: Cocktail",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Glass: Highball glass",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• White rum")
                    Text("• Lime juice")
                    Text("• Mint")
                    Text("• Soda water")
                    Text("• Sugar")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Muddle mint leaves with sugar and lime juice. " +
                                "Add rum, fill the glass with ice, top with soda water. Stir gently."
                    )
                }
            }
        }
    }
}
