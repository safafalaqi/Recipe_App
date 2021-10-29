package com.example.recipeapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val pk:Int =0,
    var title: String,
    var author: String,
    var ingredients: String,
    var instructions: String
):Serializable
