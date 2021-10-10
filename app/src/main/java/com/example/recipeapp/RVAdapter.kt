package com.example.recipeapp
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemRowBinding


class RVAdapter(private val recipes: Recipes ,val context:Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

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
        }

    override fun getItemCount()= recipes.size

}