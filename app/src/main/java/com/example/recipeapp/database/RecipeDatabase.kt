package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class],version = 1,exportSchema = false)
abstract class RecipeDatabase: RoomDatabase()  {
    companion object{
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context):RecipeDatabase {
            if (INSTANCE != null) {
                return INSTANCE as RecipeDatabase
            }
            synchronized(this){  //for the protection purpose from concurrent execution on multi threading
                val instance = Room.databaseBuilder(context.applicationContext, RecipeDatabase::class.java, "recipes"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun recipeDao(): RecipeDao

}