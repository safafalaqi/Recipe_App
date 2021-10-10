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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecipeActivity : AppCompatActivity() {
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
        //show progress Dialog
        val progressDialog = ProgressDialog(this@AddRecipeActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if(title.isNotEmpty()|| author.isNotEmpty()|| ingredient.isNotEmpty()|| instruction.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val user = RecipesItem(title,author,ingredient,instruction)
            val call: Call<RecipesItem> = apiInterface!!.addUsersInfo(user)

            call?.enqueue(object : Callback<RecipesItem?> {
                override fun onResponse(
                    call: Call<RecipesItem?>?,
                    response: Response<RecipesItem?>
                ) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<RecipesItem?>, t: Throwable) {
                    Toast.makeText(applicationContext, "Unable to add recipe.", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        }
        else {
            Toast.makeText(applicationContext, "Please do not leave it empty!", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
    //to hide the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}