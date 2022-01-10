package br.senac.gamebros.views.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.senac.gamebros.repository.Repository

class ListProductViewModelFactory(
    private val repository: Repository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListProductViewModel(repository) as T
    }
}