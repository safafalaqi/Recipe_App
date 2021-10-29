package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.recipeapp.database.Recipe


class ViewRecipeActivity : AppCompatActivity() {
    lateinit var recipe: Recipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recipe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            recipe = intent.getSerializableExtra("recipes") as Recipe
        }

        val title=findViewById<TextView>(R.id.etTitleView)
        val author=findViewById<TextView>(R.id.etAuthorView)
        val ingredient=findViewById<TextView>(R.id.etIngView)
        val instruction=findViewById<TextView>(R.id.etInstuctView)

        title.text=recipe.title
        author.text=recipe.author
        ingredient.text=recipe.ingredients
        instruction.text=recipe.instructions

        }
    }
