package fr.isen.elisa.thegreatestcocktailapp.network

import fr.isen.elisa.thegreatestcocktailapp.model.CategoryResponse
import fr.isen.elisa.thegreatestcocktailapp.model.CocktailResponse
import fr.isen.elisa.thegreatestcocktailapp.model.DrinkListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(
        @Query("c") category: String
    ): DrinkListResponse

    @GET("lookup.php")
    suspend fun getCocktailById(
        @Query("i") id: String
    ): CocktailResponse
}