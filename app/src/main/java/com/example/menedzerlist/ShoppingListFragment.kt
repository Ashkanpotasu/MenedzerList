package com.example.menedzerlist

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.menedzerlist.databinding.FragmentListItemBinding

class ShoppingListFragment : Fragment() {
    private val viewModel: ShoppingViewModel by activityViewModels {
        ShoppingViewModelFactory((activity?.application as ShoppingApplication).database.itemDao())
    }

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
}