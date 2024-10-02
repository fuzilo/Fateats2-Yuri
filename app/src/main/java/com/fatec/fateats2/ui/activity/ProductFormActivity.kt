package com.fatec.fateats2.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fatec.fateats2.R
import com.fatec.fateats2.dao.ProductDao
import com.fatec.fateats2.model.Product
import com.fatec.fateats2.ui.screens.HomeScreen
import com.fatec.fateats2.ui.theme.Fateats2Theme
import java.math.BigDecimal
import java.text.DecimalFormat

class ProductFormActivity : ComponentActivity() {

    private val dao = ProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fateats2Theme{
                Surface {
                    ProductFormScreen(onSaveClick = { product ->
                        dao.save(product)
                        finish()
                    })
                }
            }
        }
    }

}

@Composable
fun ProductFormScreen(
    onSaveClick: (Product) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier)
        Text(
            text = "Criando o produto",
            Modifier.fillMaxWidth(),
            fontSize = 28.sp,
        )
        var url by remember {
            mutableStateOf("")
        }
        if (url.isNotBlank()) {
            AsyncImage(
                model = url, contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder)
            )
        }
        TextField(
            value = url,
            onValueChange = {
                url = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = "Url da imagem")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            )
        )
        var name by remember {
            mutableStateOf("")
        }
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )
        var price by remember {
            mutableStateOf("")
        }
        val formatter = remember {
            DecimalFormat("#.##")
        }
        TextField(
            value = price,
            onValueChange = {
                try {
                    price = formatter.format(BigDecimal(it))
                } catch (e: IllegalArgumentException) {
                    if(it.isBlank()) {
                        price = it
                    }
                }
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = "Preço")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        PriceField(
            price= price,
            onPriceChange= {  price=it }
        )
        var description by remember {
            mutableStateOf("")
        }
        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),//trava de altura minima, componente se adapta ao texto
            //.height(min = 100.dp) trava de altura maxima, ativa scroll do component
            label = {
                Text(text = "Descrição")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            )
        )
        Button(
            onClick = {
                val convertedPrice = try {
                    BigDecimal(price)
                } catch (e: NumberFormatException) {
                    BigDecimal.ZERO
                }
                val product = Product(
                    name = name,
                    image = url,
                    price = convertedPrice,
                    description = description
                )
                Log.i("ProductFormActivity", "ProductFormScreen: $product")
                onSaveClick(product)
            },
            Modifier.fillMaxWidth(),
        ) {
            Text(text = "Salvar")
        }
        Spacer(modifier = Modifier)
    }
}

@Composable
fun PriceField(
    price: String,
    onPriceChange: (String) -> Unit,
) {
    TextField(
        value = price,
        onValueChange = onPriceChange,
        label = { Text("Preço") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier
            .fillMaxWidth()
    )
}



@Preview
@Composable
fun ProductFormScreenPreview() {
    Fateats2Theme {
        Surface {
            ProductFormScreen()
        }
    }
}