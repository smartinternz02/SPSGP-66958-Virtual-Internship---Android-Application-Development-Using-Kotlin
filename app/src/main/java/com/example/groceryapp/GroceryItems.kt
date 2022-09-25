package com.example.groceryapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_items")
data class GroceryItems (

    @ColumnInfo(name="itemName")
    var itemName:String,

    @ColumnInfo(name="ItemQuantity")
    var ItemQuantity:Int,

    @ColumnInfo(name= "ItemPrice")
    var ItemPrice:Double,
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null
}