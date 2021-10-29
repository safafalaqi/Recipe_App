package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.database.RecipeDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var myRv: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    private lateinit var addRecipe: FloatingActionButton
    private val recipeDao by lazy{ RecipeDatabase.getInstance(this).recipeDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //to hide the app name bar
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        //declare UI elements in main activity
        addRecipe = findViewById(R.id.btAdd)
        myRv = findViewById(R.id.rvRecipes)
        CoroutineScope(Dispatchers.IO).launch {
            val list= recipeDao.getRecipes()
            withContext(Dispatchers.Main) {
                rvAdapter = RVAdapter(list, this@MainActivity)
                myRv.adapter = rvAdapter
                myRv.layoutManager = LinearLayoutManager(applicationContext)
            }
        }

        addRecipe.setOnClickListener{
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

}