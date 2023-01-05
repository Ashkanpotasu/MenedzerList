package com.example.menedzerlist

import android.app.Application
import com.example.menedzerlist.data.ItemRoomDatabase

class ShoppingApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}