package com.example.recipeappfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeappfirebase.data.Recipe
import com.example.recipeappfirebase.databinding.FragmentAddBinding

class FragmentAdd : Fragment() {
    private var _binding: FragmentAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var myViewModel: RecipeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         var view = inflater.inflate(R.layout.fragment_add, container, false)
         _binding = FragmentAddBinding.inflate(inflater, container, false)
         view = binding.root

          myViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        binding.btView.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }

        val title=binding.etTitle
        val author=binding.etAuthor
        val ingredient=binding.etIng
        val instruction=binding.etInstuct


        binding.btSave.setOnClickListener{
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
     binding.btView.setOnClickListener{
         findNavController().navigate(R.id.action_fragmentAdd_to_fragmentHome)
     }
        return view
    }

    private fun addRecipe(title: String, author: String, ingredient: String, instruction: String) {

        //check if user inputs are not empty
        if(title.isNotEmpty()|| author.isNotEmpty()|| ingredient.isNotEmpty()|| instruction.isNotEmpty()) {
            myViewModel.addRecipe(Recipe("",title,author,ingredient,instruction))
            Toast.makeText(activity, "Added Successfully!", Toast.LENGTH_SHORT)
                .show()
        }
        else {
            Toast.makeText(activity, "Please do not leave it empty!", Toast.LENGTH_SHORT).show()

        }
    }

    //to hide the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}