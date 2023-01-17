package com.example.menedzerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.getFormattedPrice
import com.example.menedzerlist.databinding.ShoppingListFragmentBinding

class ShoppingListAdapter(private val onItemClicked: (Item) -> Unit) :
    ListAdapter<Item, ShoppingListAdapter.ItemViewHolder>(DiffCallBack) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ShoppingListFragmentBinding.inflate(LayoutInflater.from(parent.context)))
    }


    class ItemViewHolder(private var binding: ShoppingListFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.getFormattedPrice()
                itemQuantity.text = item.itemQuantity.toString()
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemName == newItem.itemName
            }

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}