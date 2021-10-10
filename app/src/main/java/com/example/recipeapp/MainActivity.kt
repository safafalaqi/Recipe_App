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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var myRv: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    private lateinit var addRecipe: FloatingActionButton

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

        createApiInterface()

        addRecipe.setOnClickListener{
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

    }

    fun createApiInterface()
    {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<Recipes?>? = apiInterface!!.getUsersInfo()

        call?.enqueue(object : Callback<Recipes?> {
            override fun onResponse(
                call: Call<Recipes?>?,
                response: Response<Recipes?>
            ) {
                progressDialog.dismiss()

                rvAdapter=RVAdapter(response.body()!!,this@MainActivity)
                myRv.adapter = rvAdapter
                myRv.layoutManager = LinearLayoutManager(applicationContext)
            }

            override fun onFailure(call: Call<Recipes?>, t: Throwable?) {
                Toast.makeText(applicationContext,"Unable to load data!", Toast.LENGTH_SHORT).show()
                 progressDialog.dismiss()
                call.cancel()
            }
        })
    }
}