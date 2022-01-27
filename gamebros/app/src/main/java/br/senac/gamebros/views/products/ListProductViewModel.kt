package br.senac.gamebros.views.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senac.gamebros.model.Product
import br.senac.gamebros.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ListProductViewModel(val repository: Repository, categoryId: Int) : ViewModel() {
    val myResponse = MutableLiveData<Response<List<Product>>>()

    fun listarProdutos(categoryId: Int) {
        viewModelScope.launch {
            val response = repository.listarProdutos(categoryId)
            myResponse.value = response
        }
    }
}