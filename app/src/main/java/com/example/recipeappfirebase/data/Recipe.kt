package com.example.recipeappfirebase.data

import java.io.Serializable

data class Recipe(
    var pk:String? =null,
    var title: String,
    var author: String,
    var ingredients: String,
    var instructions: String
):Serializable
