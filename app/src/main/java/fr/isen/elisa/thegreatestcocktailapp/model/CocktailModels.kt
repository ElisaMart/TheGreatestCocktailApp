package fr.isen.elisa.thegreatestcocktailapp.model

data class CocktailResponse(
    val drinks: List<Drink>?
)

data class CategoryItem(
    val strCategory: String
)

data class CategoryResponse(
    val drinks: List<CategoryItem>?
)

data class DrinkItem(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
)

data class DrinkListResponse(
    val drinks: List<DrinkItem>?
)

data class Drink(
    val idDrink: String,
    val strDrink: String?,
    val strCategory: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?
) {
    fun ingredientsList(): List<String> {
        return listOfNotNull(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
            strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
        ).filter { it.isNotBlank() }
    }
}