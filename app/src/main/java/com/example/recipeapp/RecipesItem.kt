package com.example.recipeapp
import java.io.Serializable

data class RecipesItem(
    val title: String,
    val author: String,
    val ingredients: String,
    val instructions: String
):Serializable
