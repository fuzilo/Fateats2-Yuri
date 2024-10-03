package com.fatec.fateats2.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.fateats2.model.Product
import com.fatec.fateats2.ui.components.CustomTopAppBar
import com.fatec.fateats2.ui.screens.HomeScreen
import com.fatec.fateats2.ui.theme.Fateats2Theme

class ProductFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fateats2Theme {
                Surface {
                    ProductFormScreen {

                    }
                }
            }
        }
    }
}

@Composable
fun ProductFormScreen(onSaveClick:(Product) -> Unit{}){
    CustomTopAppBar(title = "Textão Bonito")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Spacer(modifier = Modifier.padding(6.dp))
        var url by remember{
            mutableStateOf("")
        }
    }

}


@Preview
@Composable
fun ProductFormScreenPreview(onSaveClick:(Product) -> Unit) {
    Fateats2Theme {
        Surface {
            ProductFormScreen() {

            }
        }
    }
}


