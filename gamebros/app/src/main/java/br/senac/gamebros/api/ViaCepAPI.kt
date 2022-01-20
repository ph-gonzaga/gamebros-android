package br.senac.gamebros.api

import br.senac.gamebros.services.ViaCepService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ViaCepAPI {
    private const val baseUrl = "https://viacep.com.br/ws/"
    private const val timeOut = 30L

    private val retrofit: Retrofit
        get() {
            val okHttp = OkHttpClient
                .Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build()

            return Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
        }

    val cep: ViaCepService
        get() {
            return retrofit.create(ViaCepService::class.java)
        }
}