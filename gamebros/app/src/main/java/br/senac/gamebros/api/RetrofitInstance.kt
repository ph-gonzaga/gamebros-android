package br.senac.gamebros.api

import android.content.Context
import br.senac.gamebros.services.AuthToken
import br.senac.gamebros.services.LoginService
import br.senac.gamebros.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private lateinit var context: Context
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitSeguro: Retrofit
        get() {
            val autenticador = AuthToken(context)
            val okHttp = OkHttpClient.Builder()
                .readTimeout(30L, TimeUnit.SECONDS)
                .connectTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(autenticador)
                .authenticator(autenticador)
                .build()

            return  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
        }

    val productsAPI : API by lazy {
        retrofit.create(API::class.java)
    }

    val login: LoginService
        get(){
            return retrofit.create(LoginService::class.java)
        }
}