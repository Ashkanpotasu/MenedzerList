package com.example.menedzerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.menedzerlist.data.Item
import com.example.menedzerlist.data.ItemDao

class ShoppingViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()
}