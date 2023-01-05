package com.example.menedzerlist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Update
    suspend fun update(shoppingItem: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingItem: Item)

    @Delete
    suspend fun delete(shoppingItem: Item)

    @Query("select * from shopping_table")
    fun getItems(): Flow<List<Item>>

    @Query("select * from shopping_table where id=:id")
    fun getItem(id: Int): Flow<Item>
}