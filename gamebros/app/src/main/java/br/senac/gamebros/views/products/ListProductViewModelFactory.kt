package br.senac.gamebros.views.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.senac.gamebros.repository.Repository

class ListProductViewModelFactory(
    private val repository: Repository,
    categoryId: Int
    ) : ViewModelProvider.Factory {

    private val categoryId = categoryId


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return categoryId?.let { ListProductViewModel(repository, it) } as T
    }
}