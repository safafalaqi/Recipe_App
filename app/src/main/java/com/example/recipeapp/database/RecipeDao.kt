package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipes")
    fun getRecipes(): LiveData<List<Recipe>>

    @Update
    suspend fun updateRecipe(note: Recipe)

    @Delete
    suspend fun deleteRecipe(note: Recipe)

    @Query("DELETE FROM Recipes")
    suspend fun deleteAll() : Int
}