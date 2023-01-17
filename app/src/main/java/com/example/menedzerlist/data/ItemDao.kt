package com.example.menedzerlist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Update
    suspend fun update(item: Item)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("select * from shopping_table")
    fun getItems(): Flow<List<Item>>

    @Query("select * from shopping_table where id=:id")
    fun getItem(id: Int): Flow<Item>
}