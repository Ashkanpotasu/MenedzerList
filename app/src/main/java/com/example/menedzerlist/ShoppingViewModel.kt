package com.example.menedzerlist

import androidx.lifecycle.*
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.ItemDao
import kotlinx.coroutines.launch

class ShoppingViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun addItem(itemName: String, itemPrice: String, itemQuantity: String) {
        val item = Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            itemQuantity = itemQuantity.toInt()
        )
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun updateItem(id: Int, itemName: String, itemPrice: String, itemQuantity: String) {
        val updatedItem = Item(
            id = id,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            itemQuantity = itemQuantity.toInt()
        )

        viewModelScope.launch {
            itemDao.update(updatedItem)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemQuantity: String): Boolean {
        return !(itemName.isNullOrBlank() || itemPrice.isNullOrBlank() || itemQuantity.isNullOrBlank())
    }
}

class ShoppingViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}