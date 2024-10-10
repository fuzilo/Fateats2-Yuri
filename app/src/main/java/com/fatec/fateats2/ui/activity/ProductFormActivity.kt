package com.fatec.fateats2.ui.activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fatec.fateats2.R
import com.fatec.fateats2.dao.ProductDao
import com.fatec.fateats2.model.Product
import com.fatec.fateats2.ui.theme.Fateats2Theme
import java.math.BigDecimal
import kotlin.random.Random

class ProductFormActivity : ComponentActivity() {
    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val productId = intent.getIntExtra("productId",0)
            Log.i("ProductItem", "click")
            Fateats2Theme {
                Surface {
                    val productParam = dao.getProductById(id=productId)?: productDefault()
                    if (productId != 0){
                        ProductFormScreen(
                            onSaveClick = { product ->
                            dao.save(product = product)
                            finish()
                        },
                            product = productParam)
                    }else {
                        ProductFormScreen(onSaveClick = { product ->
                            dao.save(product = product)
                            finish()
                        })
                    }
                }
            }
        }
    }
     fun  productDefault() = Product(
         id=0,
         name= "",
         price = BigDecimal.ZERO,
         image = "",
         description = ""

     )

    @Composable
    fun ProductFormScreen(
        onSaveClick: (Product) -> Unit={},
        product: Product = productDefault()
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.padding(6.dp))

            var url by remember {
                mutableStateOf(product.image.toString())
            }
            if (url.isNotBlank()) {
                AsyncImage(
                    model = url, contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder)
                )

            }

            // Campo de entrada para o URL
            TextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("URL da imagem") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            var name by remember {
                mutableStateOf(product.name)
            }
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nome") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Go,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            val formatter = remember {
                DecimalFormat("#.##")
            }
            var price by remember {
                mutableStateOf("")
            }
            TextField(
                value = price,
                onValueChange = {
                    try {
                        price = formatter.format(BigDecimal(it))
                    } catch (e: IllegalArgumentException) {
                        if (it.isBlank()) {
                            price = it
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Preço") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Go,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            var description by remember {
                mutableStateOf(product.description.toString())
            }
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                label = { Text(text = "Descrição") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Go,
                    capitalization = KeyboardCapitalization.Words
                )
            )

            // Botão de salvar
            Button(
                onClick = {
                    val convertedPrice = try {
                        BigDecimal(price)
                    } catch (e: NumberFormatException) {
                        BigDecimal.ZERO
                    }
                    val product = Product(
                        id= Random(1234).nextInt(),
                        name = name,
                        image = url,
                        price = convertedPrice,
                        description = description
                    )
                    Log.i("ProductFormActivity","ProductFormActivity,$product")
                    onSaveClick(product)
                },
                modifier = Modifier.padding(top = 20.dp)

            ) {
                Text("Salvar")
            }
        }
    }
}


//    @Preview
//    @Composable
//    fun ProductFormScreenPreview(onSaveClick: (Product) -> Unit = {}) {
//        Fateats2Theme {
//            Surface {
//                ProductFormScreen()
//            }
//        }
//    }


