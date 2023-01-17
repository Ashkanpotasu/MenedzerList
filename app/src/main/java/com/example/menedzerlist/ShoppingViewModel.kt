package com.example.menedzerlist

import androidx.lifecycle.*
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun addItem(itemName: String, itemQuantity: String, itemPrice: String) {
        val item = Item(itemName = itemName, itemQuantity = itemQuantity.toInt(), itemPrice = itemPrice.toDouble())
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun isAvailableQuantity(item: Item): Boolean {
        return (item.itemQuantity > 0)
    }

    fun sellItem(item: Item) {
        if (item.itemQuantity > 0) {
            val newItem = item.copy(itemQuantity = item.itemQuantity - 1)
            updateItem(newItem)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemQuantity: String, itemPrice: String): Boolean {
        return !(itemName.isNullOrBlank() || itemQuantity.isNullOrBlank() || itemPrice.isNullOrBlank())
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