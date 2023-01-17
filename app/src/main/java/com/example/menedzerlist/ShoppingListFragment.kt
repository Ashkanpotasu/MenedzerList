package com.example.menedzerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menedzerlist.databinding.ShoppingListFragmentBinding

class ShoppingListFragment : Fragment() {
    private val viewModel: ShoppingViewModel by activityViewModels {
        ShoppingViewModelFactory((activity?.application as ShoppingApplication).database.itemDao())
    }

    private var _binding: ShoppingListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShoppingListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ShoppingListAdapter {
            val action =
                ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floatingActionButton.setOnClickListener {
            val action =
                ShoppingListFragmentDirections.actionShoppingListFragmentToAddItemFragment("Dodaj")
            this.findNavController().navigate(action)
        }
    }
}