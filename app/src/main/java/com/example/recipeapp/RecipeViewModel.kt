package com.example.recipeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.database.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application):AndroidViewModel(application) {
    val recipes:LiveData<List<Recipe>>
    val repository:RecipeRepository

    init {
        val dao = RecipeDatabase.getInstance(application).recipeDao()
        repository = RecipeRepository(dao)
        recipes = repository.getRecipes
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch( Dispatchers.IO ){
        repository.delete(recipe)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch( Dispatchers.IO ){
        repository.update(recipe)
    }

    fun addRecipe(recipe: Recipe) = viewModelScope.launch( Dispatchers.IO ){
        repository.insert(recipe)
    }

    fun deleteAll() = viewModelScope.launch( Dispatchers.IO ){
        repository.deleteAll()
    }


}