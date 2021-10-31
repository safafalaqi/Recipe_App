package com.example.recipeapp.database

import androidx.lifecycle.LiveData

class RecipeRepository (private val dao:RecipeDao) {

    val getRecipes: LiveData<List<Recipe>> = dao.getRecipes()

    suspend fun insert(recipe: Recipe){
        dao.addRecipe(recipe)
    }

    suspend fun update(recipe: Recipe){
        dao.updateRecipe(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }


}