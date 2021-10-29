package com.example.recipeapp.database

import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipes")
    fun getRecipes(): List<Recipe>

    @Update
    suspend fun updateRecipe(note: Recipe)

    @Delete
    suspend fun deleteRecipe(note: Recipe)
}