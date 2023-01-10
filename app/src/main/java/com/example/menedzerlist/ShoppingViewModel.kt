package com.example.menedzerlist

import androidx.lifecycle.*
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun addItem(itemName: String, itemQuantity: Int, itemPrice: Double) {
        val item = Item(itemName = itemName, itemQuantity = itemQuantity, itemPrice = itemPrice)
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    fun updateItem(id: Int, itemName: String, itemQuantity: Int, itemPrice: Double) {
        val item =
            Item(id = id, itemName = itemName, itemQuantity = itemQuantity, itemPrice = itemPrice)
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.update(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }
}
class ShoppingViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}