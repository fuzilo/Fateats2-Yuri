package com.fatec.fateats2.ui.components


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.fateats2.model.Product
import com.fatec.fateats2.sampledata.sampleProducts
import com.fatec.fateats2.ui.activity.ProductFormActivity
import com.fatec.fateats2.ui.theme.Fateats2Theme

@Composable
fun ProductsSection(
    title: String,
    products: List<Product>,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Column(
        modifier = modifier
    ) {
        val productLauncher=
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) {result->
                // Aqui voce pode tratar o resultado da sua Activity
                //por exemplo verificar se o cadastro ou edicao de produto deu certo
            }

        Text(
            text = title,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            fontSize = 20.sp,
            fontWeight = FontWeight(400)
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(products){ product ->
                ProductItem(product = product,
                    onClickProductItem = {
                        Log.i("ProductItem", "$product")
                        val intent = Intent(context, ProductFormActivity::class.java)
                        intent.putExtra("productId", product.id)
                        productLauncher.launch(intent)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductsSectionPreview() {
    Fateats2Theme {
        Surface {
            ProductsSection(
                "Promoções",
                products = sampleProducts
            )
        }
    }
}