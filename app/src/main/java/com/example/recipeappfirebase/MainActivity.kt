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
import com.example.recipeappfirebase.adapter.RVAdapter
import com.example.recipeappfirebase.data.Recipe


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to hide the app name bar
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

    }

}

