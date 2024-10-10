package com.fatec.fateats2.model

import java.math.BigDecimal

data class Product(
    val id:Int = 0,
    val name:String,
    val price:BigDecimal,
    val image:String? = null,
    val description:String? = null,
)
