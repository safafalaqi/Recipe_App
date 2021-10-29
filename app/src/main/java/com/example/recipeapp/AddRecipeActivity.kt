package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecipeActivity : AppCompatActivity() {
    private val recipeDao by lazy{ RecipeDatabase.getInstance(this).recipeDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addrecipe)

        val title=findViewById<EditText>(R.id.etTitle)
        val author=findViewById<EditText>(R.id.etAuthor)
        val ingredient=findViewById<EditText>(R.id.etIng)
        val instruction=findViewById<EditText>(R.id.etInstuct)
        val save=findViewById<Button>(R.id.btSave)


        save.setOnClickListener{
            //get user name and location from edit text
            addRecipe(title.text.toString(),author.text.toString(),ingredient.text.toString(),instruction.text.toString())
            title.text.clear()
            title.hideKeyboard()
            author.text.clear()
            author.hideKeyboard()
            ingredient.text.clear()
            ingredient.hideKeyboard()
            instruction.text.clear()
            instruction.hideKeyboard()
        }

        val view=findViewById<Button>(R.id.btView)
        view.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addRecipe(title: String, author: String, ingredient: String, instruction: String) {

        //check if user inputs are not empty
        if(title.isNotEmpty()|| author.isNotEmpty()|| ingredient.isNotEmpty()|| instruction.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                recipeDao.addRecipe(Recipe(0, title, author, ingredient, instruction))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddRecipeActivity, "Added Successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        else {
            Toast.makeText(this, "Please do not leave it empty!", Toast.LENGTH_SHORT).show()

        }
    }
    //to hide the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}