package com.example.recipeappfirebase.adapter
import android.app.Dialog
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
import com.example.recipeappfirebase.MainActivity
import com.example.recipeappfirebase.R
import com.example.recipeappfirebase.ViewRecipeActivity
import com.example.recipeappfirebase.data.Recipe
import com.example.recipeappfirebase.databinding.ItemRowBinding


class RVAdapter(val mainActivity: MainActivity): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

     var recipes= emptyList<Recipe>()

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
                val intent = Intent(mainActivity, ViewRecipeActivity::class.java)
                intent.putExtra("recipes", recipes[position])
            mainActivity.startActivity(intent)
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
        val dialog = Dialog(mainActivity)
        val dialogview = LayoutInflater.from(mainActivity)
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
                    recipes[i].title=title.text.toString()
                    recipes[i].author=author.text.toString()
                    recipes[i].ingredients=ingredients.text.toString()
                    recipes[i].instructions=instruction.text.toString()
                    mainActivity.myViewModel.updateRecipe(recipes[i])
                    dialog.dismiss()
                }else
                {
                Toast.makeText(mainActivity, " can not be empty!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        btClose.setOnClickListener {
            dialog.cancel()
            notifyDataSetChanged()
        }
    }

    fun delete(recipe: Recipe){
        val dialogBuilder = android.app.AlertDialog.Builder(mainActivity)
        dialogBuilder.setMessage("Are you sure you want to delete the note?")
            // negative button text and action
            .setPositiveButton("yes", DialogInterface.OnClickListener {
                    dialog, id ->
                    mainActivity.myViewModel.deleteRecipe(recipe)

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.cancel()
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