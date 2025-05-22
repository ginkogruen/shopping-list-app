package com.example.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Main shopping list
        val shoppingList = mutableListOf("Apples", "Bananas", "Oranges", "Grapes", "Mangoes")

        setContent {
            ShoppingListAppTheme {
                Scaffold(
                    topBar = { AppBar() },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            ShoppingList(items = shoppingList)
                        }
                    }
                )
            }
        }
    }
}

// App Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Shopping List",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.ui.graphics.Color.Green)
    )
}

@Composable
fun ShoppingList(items: List<String>) {
    val checkedItems = remember { mutableStateListOf<Boolean>().apply {
        addAll(List(items.size) { false })
    }}

    LazyColumn {
        itemsIndexed(items) { index, item ->
            ShoppingListItem(
                item = item,
                checked = checkedItems[index],
                onCheckedChange = { isChecked ->
                    checkedItems[index] = isChecked
                }
            )
            if (index < items.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: String, onCheckedChange: (Boolean) -> Unit, checked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = item,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}