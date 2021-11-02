package com.example.recipeappfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappfirebase.adapter.RVAdapter
import com.example.recipeappfirebase.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var myRv: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var  myViewModel:RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        view = binding.root


        /*when the fragment get the ViewModelProvider, it received the same
       SharedViewModel instance,*/
        myViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        myViewModel.recipes.observe(viewLifecycleOwner) { list ->
            list?.let { rvAdapter.updateRV(list) }
        }

        setRV()



        binding.btAdd.setOnClickListener {
         //navigate to add fragment
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentAdd)
        }



        return view
    }

    private fun setRV() {
        myRv = binding.rvRecipes
        rvAdapter = RVAdapter( this)
        myRv.adapter = rvAdapter
        myRv.layoutManager = LinearLayoutManager( requireContext())
    }

}