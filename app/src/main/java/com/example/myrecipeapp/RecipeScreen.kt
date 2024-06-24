package com.example.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(navigateToDetail: (Category) -> Unit,
                 fetchingState: MainViewModel.RecipeState) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            fetchingState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            fetchingState.error != null -> {
                Text("Error occurred: ${fetchingState.error}")
            }

            else -> {
                CategoryScreen(categories = fetchingState.list, navigateToDetail)
            }
        }
    }
}

@Composable
fun CategoryScreen(categories: List<Category>, navigateToDetail: (Category) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(categories) {
            CategoryItem(category = it, navigateToDetail)
        }
    }
}

@Composable
fun CategoryItem(category: Category, navigateToDetail: (Category) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = rememberAsyncImagePainter(model = category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .clickable {
                    navigateToDetail(category)
                }
        )

        Text(
            text = category.strCategory,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top=4.dp)
        )
    }
}

