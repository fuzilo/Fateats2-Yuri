package com.fatec.fateats2.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.fateats2.model.Product
import com.fatec.fateats2.sampledata.sampleCandies
import com.fatec.fateats2.sampledata.sampleDrinks
import com.fatec.fateats2.sampledata.sampleProducts
import com.fatec.fateats2.sampledata.sampleSections
import com.fatec.fateats2.ui.components.CardProductItem
import com.fatec.fateats2.ui.components.CustomTopAppBar
import com.fatec.fateats2.ui.components.ProductsSection
import com.fatec.fateats2.ui.components.SearchTextField
import com.fatec.fateats2.ui.theme.Fateats2Theme

class HomeScreenUiState(
    val sections: Map<String, List<Product>> = emptyMap(),
    val searchedProducts: List<Product> = emptyList(),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {}
) {
    fun isShowSections(): Boolean {
        return searchText.isBlank()
    }
}

@Composable
fun HomeScreen(products: List<Product> = sampleProducts) {
    val sections = mapOf(
        "Todos Produtos" to products,
        "Promoção" to sampleDrinks + sampleCandies,
        "Doces" to sampleCandies,
        "Bebidas" to sampleDrinks
    )
    var text by remember { mutableStateOf("") }

    fun containsInNameOrDescrioption() = { product: Product ->
        product.name.contains(
            text,
            ignoreCase = true,
        ) || product.description?.contains(
            text,
            ignoreCase = true,
        ) ?: false
    }

    val searchedProducts = remember(text, products) {
        if (text.isNotBlank()) {
            (sampleProducts + products).filter(containsInNameOrDescrioption())
        } else emptyList()
    }

    val state = remember(products, text) {
        HomeScreenUiState(
            sections = sections,
            searchedProducts = searchedProducts,
            searchText = text,
            onSearchChange = {
                text = it
            }
        )
    }



    HomeScreen(state = state)
}

@Composable
fun HomeScreen(
    state: HomeScreenUiState = HomeScreenUiState()
) {
    Column {
        val sections = state.sections
        val text = state.searchText
        val searchedProducts = state.searchedProducts

        CustomTopAppBar(title = "Home")

        SearchTextField(
            searchText = text,
            onSearchChange = state.onSearchChange,
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
        var showTable by remember { mutableStateOf(true) }
        var hide by remember { mutableStateOf(true) }
        Row {
            OutlinedButton(onClick = { showTable = true }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
                Text(text = "Home")

            }
            OutlinedButton(onClick = { showTable = false }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                Text(text = "Busca")
            }
            Column {
                OutlinedButton(onClick = { hide = false }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Mostrar Produtos"
                    )

                }
                OutlinedButton(onClick = { hide = true }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Esconder Produtos"
                    )

                }
            }
        }
        if (!hide) {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (showTable) {
                    for (section in sections) {
                        val title = section.key
                        val products = section.value
                        item {
                            ProductsSection(
                                title = title,
                                products = products
                            )
                        }
                    }
                } else {
                    items(searchedProducts) { p ->
                        CardProductItem(
                            product = p,
                            Modifier.padding(horizontal = 16.dp),
                        )
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    Fateats2Theme {
        Surface {
            HomeScreen(HomeScreenUiState(sections = sampleSections))
        }
    }
}

@Preview
@Composable
fun HomeScreenWithSearchTextPreview() {
    Fateats2Theme {
        Surface {
            HomeScreen(
                state = HomeScreenUiState(
                    searchText = "a",
                    sections = sampleSections
                ),
            )
        }
    }
}