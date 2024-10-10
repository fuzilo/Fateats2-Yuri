package com.fatec.fateats2.dao

import androidx.compose.runtime.mutableStateListOf
import com.fatec.fateats2.model.Product

class ProductDao {
    companion object{
        private val products = mutableStateListOf<Product>()
    }

    fun products() = products.toList()

    fun save(product: Product){
        products.add(product)
    }

    fun getProductById(id:Int):Product?{
        return products.find{it.id == id}
    }
}