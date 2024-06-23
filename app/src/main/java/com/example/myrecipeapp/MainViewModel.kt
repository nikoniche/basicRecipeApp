package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    data class RecipeState(
        var loading: Boolean = false,
        var error: String? = null,
        var list: List<Category> = emptyList()
    )

    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState // for UI composable

    init {
        fetchCategories() // load categories on initialization (app start)
    }

    private fun fetchCategories() {
        viewModelScope.launch{
            try {
                val response: CategoryResponse = recipeService.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    error = null,
                    list = response.categories
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    error = "Failed loading categories: ${e.message}"
                )
            } finally {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false
                )
            }
        }
    }
}