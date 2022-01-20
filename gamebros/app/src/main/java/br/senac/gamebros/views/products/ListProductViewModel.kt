package br.senac.gamebros.views.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.senac.gamebros.model.Product
import br.senac.gamebros.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ListProductViewModel(val repository: Repository) : ViewModel() {
    val myResponse = MutableLiveData<Response<List<Product>>>()

    fun listarProdutos() {
        viewModelScope.launch {
            val response = repository.listarProdutos()
            myResponse.value = response
        }
    }
}