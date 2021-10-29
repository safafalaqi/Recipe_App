package com.example.recipeapp
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.databinding.ItemRowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RVAdapter(private var recipes: List<Recipe>, val context:Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    private val recipeDao by lazy{ RecipeDatabase.getInstance(context).recipeDao() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val title=recipes.get(position).title
        val author=recipes.get(position).author
        holder.binding.apply {
            tvTitle.text = title
            tvAuthor.text = "By: "+ author
        }
        holder.itemView.setOnClickListener{
                val intent = Intent(context, ViewRecipeActivity::class.java)
                intent.putExtra("recipes", recipes[position])
                context.startActivity(intent)
            }
        holder.binding.btimgdel.setOnClickListener{
            delete(recipes[position])
        }
        holder.binding.btimgupdate.setOnClickListener{
            update(position)
        }
        }

    override fun getItemCount()= recipes.size


    fun update(i:Int) {

        val dialog = Dialog(context)
        val dialogview = LayoutInflater.from(context)
            .inflate(R.layout.dialog_custom, null, false)
        //initializing dialog screen
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog?.show()

        val title = dialog.findViewById<EditText>(R.id.etTitleD)
        val author = dialog.findViewById<EditText>(R.id.etAuthorD)
        val ingredients = dialog.findViewById<EditText>(R.id.etIngD)
        val instruction = dialog.findViewById<EditText>(R.id.etInstuctD)
        val update = dialog.findViewById<Button>(R.id.btSaveD)
        val btClose = dialog.findViewById<AppCompatImageButton>(R.id.imgBtClose)

        title.setText(recipes[i].title)
        author.setText(recipes[i].author)
        ingredients.setText(recipes[i].ingredients)
        instruction.setText(recipes[i].instructions)

        update.setOnClickListener {
            if (title.text.isNotBlank() && author.text.isNotBlank()&&
                ingredients.text.isNotBlank()&& instruction.text.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    recipes[i].title=title.text.toString()
                    recipes[i].author=author.text.toString()
                    recipes[i].ingredients=ingredients.text.toString()
                    recipes[i].instructions=instruction.text.toString()
                    recipeDao.updateRecipe(recipes[i])
                    recipes = recipeDao.getRecipes()
                    withContext(Dispatchers.Main){ notifyDataSetChanged()}
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(context, " can not be empty!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        btClose.setOnClickListener {
            dialog.cancel()
            notifyDataSetChanged()
        }
    }

    fun delete(recipe: Recipe){
        val dialogBuilder = android.app.AlertDialog.Builder(context)
        dialogBuilder.setMessage("Are you sure you want to delete the note?")
            // negative button text and action
            .setPositiveButton("yes", DialogInterface.OnClickListener {
                    dialog, id ->
                CoroutineScope(Dispatchers.IO).launch {
                    recipeDao.deleteRecipe(recipe)
                    recipes = recipeDao.getRecipes()
                    withContext(Dispatchers.Main){ notifyDataSetChanged()}
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
                notifyDataSetChanged()
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }

    fun updateRV(lessonsList:List<Recipe>){
        recipes=lessonsList
        notifyDataSetChanged()
    }
}