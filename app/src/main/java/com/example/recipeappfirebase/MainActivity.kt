package com.example.recipeappfirebase



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappfirebase.databinding.ActivityMainBinding
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.recipeappfirebase.adapter.RVAdapter
import com.example.recipeappfirebase.data.Recipe


class MainActivity : AppCompatActivity() {
    private lateinit var myRv: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var binding: ActivityMainBinding

    //ViewModelProviders can only instantiate ViewModels with no arg constructor.
    val myViewModel by lazy{ ViewModelProvider(this).get(RecipeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //to hide the app name bar
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }


        myViewModel.recipes.observe(this, {list->
            list?.let { rvAdapter.updateRV(it) }
        })

        //use bending view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myRv = binding.rvRecipes
                rvAdapter = RVAdapter( this)
                myRv.adapter = rvAdapter
                myRv.layoutManager = LinearLayoutManager(applicationContext)


        binding.btAdd.setOnClickListener {

         addRecipe()
        }


    }

    private fun addRecipe() {
        binding.llMain.isVisible=false
        binding.lladdRecipe.isVisible=true


        val title=binding.etTitle.text
        val author=binding.etAuthor.text
        val ingredient=binding.etIng.text
        val instruction=binding.etInstuct.text

        binding.btSave.setOnClickListener{
            //get user name and location from edit text
            //check if user inputs are not empty
            if(title.isNotEmpty()|| author.isNotEmpty()
                || ingredient.isNotEmpty()|| instruction.isNotEmpty()) {
                myViewModel.addRecipe(Recipe(null,title.toString(),author.toString(),ingredient.toString(),instruction.toString()))
                Toast.makeText(this, "Added Successfully!", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                Toast.makeText(this, "Please do not leave it empty!", Toast.LENGTH_SHORT).show()

            }
            title.clear()
            binding.etTitle.hideKeyboard()
            author.clear()
            binding.etAuthor.hideKeyboard()
            ingredient.clear()
            binding.etIng.hideKeyboard()
            instruction.clear()
            binding.etInstuct.hideKeyboard()
        }


        binding.btView.setOnClickListener{
            binding.llMain.isVisible=true
            binding.lladdRecipe.isVisible=false
        }


    }
    //to hide the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}

