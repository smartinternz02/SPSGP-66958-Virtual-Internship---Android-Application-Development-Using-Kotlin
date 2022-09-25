package com.example.groceryapp

class GroceryRepository(private val db:GroceryDatabase) {
    suspend fun insert(items: GroceryItems) = db.getgrocerydao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getgrocerydao().delete(items)

    fun getAllItems() = db.getgrocerydao().getAllGroceryItems()
}