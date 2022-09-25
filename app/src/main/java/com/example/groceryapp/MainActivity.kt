package com.example.groceryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton



class MainActivity : AppCompatActivity() , groceryadapter.GroceryItemClickInterface {
    lateinit var ItemsRV:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var list:List<GroceryItems>
    lateinit var groceryadapter: groceryadapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ItemsRV = findViewById(R.id.rvitems)
        addFAB = findViewById(R.id.idFABAdd)
        list= ArrayList<GroceryItems>()
        groceryadapter=groceryadapter(list,
        this)
        ItemsRV.layoutManager = LinearLayoutManager(this)
        ItemsRV.adapter = groceryadapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this,
            Observer {
            groceryadapter.list = it
            groceryadapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener{
            openDialog()
        }
    }

    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelbtn = dialog.findViewById<AppCompatButton>(R.id.cancelbtn)
        val addbtn = dialog.findViewById<AppCompatButton>(R.id.addbtn)
        val itemEdt = dialog.findViewById<EditText>(R.id.ItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.Itemprice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.Itemquantity)
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }
        addbtn.setOnClickListener {
            val itemname:String = itemEdt.text.toString()
            val itemprice:String = itemPriceEdt.text.toString()
            val itemquantity:String = itemQuantityEdt.text.toString()
            val quant : Int = itemquantity.toInt()
            val pr : Double = itemprice.toDouble()
            if (itemname.isNotEmpty() && itemprice.isNotEmpty() && itemquantity.isNotEmpty()){
                val items = GroceryItems(itemname,quant,pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Added",Toast.LENGTH_SHORT).show()
                groceryadapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            else{
                Toast.makeText(applicationContext,"Please fill all details",Toast.LENGTH_SHORT).show()
            }

        }
        dialog.show()
    }


    override fun onItemClick(groceryItems: GroceryItems) {

        groceryViewModel.delete(groceryItems)
        groceryadapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted Successfully..",Toast.LENGTH_SHORT).show()

    }
}