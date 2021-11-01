package com.example.recipeappfirebase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeappfirebase.data.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application):AndroidViewModel(application) {
    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val db = Firebase.firestore
    val TAG = "In_RecipeViewModel"


    init{
        recipes = getFireBaseRecipes()
    }

    fun getFireBaseRecipes(): MutableLiveData<List<Recipe>> {
        val allRecipes = arrayListOf<Recipe>()
        db.collection("recipes").get().addOnSuccessListener { result ->
            result.forEach{
                allRecipes.add(
                    Recipe(it.id,
                        it.data["title"].toString(),
                        it.data["author"].toString(),
                        it.data["ingredients"].toString(),
                        it.data["instructions"].toString()))
            }
            recipes.value=allRecipes
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error occurs while getting data.", exception)
            }

        return recipes
    }


    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch( Dispatchers.IO ){
        db.collection("recipes").document(recipe.pk!!).delete()
        //to update notes
        getFireBaseRecipes()
    }

    fun updateRecipe(r: Recipe) = viewModelScope.launch( Dispatchers.IO ){
        val recipe = hashMapOf("title" to r.title,
            "author" to r.author,
            "ingredients" to r.ingredients,
            "instructions" to r.instructions)

        db.collection("recipes").document(r.pk!!).update(recipe as Map<String, Any>)
        //to update notes
        getFireBaseRecipes()
    }

    fun addRecipe(r: Recipe)  = viewModelScope.launch( Dispatchers.IO ){
        val recipe = hashMapOf("title" to r.title,
            "author" to r.author,
            "ingredients" to r.ingredients,
            "instructions" to r.instructions)
        db.collection("recipes").add(recipe) .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }.addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        //to update notes
        getFireBaseRecipes()
    }

    fun deleteAll()  = CoroutineScope(Dispatchers.IO).launch( Dispatchers.IO ){

    }
}