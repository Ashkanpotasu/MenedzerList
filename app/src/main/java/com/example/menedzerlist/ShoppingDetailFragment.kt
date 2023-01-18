package com.example.menedzerlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.getFormattedPrice
import com.example.menedzerlist.databinding.FragmentItemDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ShoppingDetailFragment : Fragment() {
    private val viewModel: ShoppingViewModel by activityViewModels {
        ShoppingViewModelFactory((activity?.application as ShoppingApplication).database.itemDao())
    }
    private val navigationArgs: ShoppingDetailFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstaceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Uwaga")
            .setMessage("Czy na pewno chcesz usunąć?")
            .setCancelable(false)
            .setNegativeButton("Nie") { _, _ -> }
            .setPositiveButton("Tak") { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun bind(item: Item) {
        binding.apply {
            binding.itemName.text = item.itemName
            binding.itemPrice.text = item.getFormattedPrice()
            binding.itemCount.text = item.itemQuantity.toString()

            //wroc.isEnabled = viewModel.isAvailableQuantity(item)
            wroc.setOnClickListener { findNavController().navigateUp() }

            deleteItem.setOnClickListener { showConfirmationDialog() }

            editItem.setOnClickListener { editItem() }
        }
    }

    private fun editItem() {
        val action = ShoppingDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            "Edytuj",
            item.id
        )
        this.findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}