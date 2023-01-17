package com.example.menedzerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private val navigationArgs: ShoppingDetailFragmentArgs by navArgs()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by activityViewModels {
        ShoppingViewModelFactory((activity?.application as ShoppingApplication).database.itemDao())
    }

    lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemCount.text.toString(),
            binding.itemPrice.text.toString()
        )
    }

    private fun addItem(){
        if(isEntryValid()){
            viewModel.addItem(
                binding.itemName.text.toString(),
                binding.itemCount.text.toString(),
                binding.itemPrice.text.toString()
            )
        }
    }
}