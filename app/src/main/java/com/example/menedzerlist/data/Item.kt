package com.example.menedzerlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "shopping_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var itemName: String,

    @ColumnInfo(name = "quantity")
    var itemQuantity: Int,

    @ColumnInfo(name = "price")
    val itemPrice: Double
)

fun Item.getFormattedPrice(): String = NumberFormat.getCurrencyInstance(Locale("pl", "PL")).format(itemPrice)