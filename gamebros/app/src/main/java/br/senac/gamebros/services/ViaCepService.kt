package br.senac.gamebros.services

import br.senac.gamebros.model.ViaCep
import retrofit2.Call
import retrofit2.http.*

interface ViaCepService {
    @GET("{cep}/json")
    fun getCep(@Path("cep") cep: String) : Call<ViaCep>
}