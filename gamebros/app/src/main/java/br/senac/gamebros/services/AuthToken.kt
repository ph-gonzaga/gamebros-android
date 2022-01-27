package br.senac.gamebros.services

import android.content.Context
import br.senac.gamebros.api.RetrofitInstance.login
import br.senac.gamebros.model.Login
import br.senac.gamebros.model.Token
import okhttp3.*

const val ARQUIVO_LOGIN = "login"

class AuthToken(private val context: Context): Interceptor, Authenticator {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prefs = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)

        val token = prefs.getString("token", "") as String

        var request = chain.request()

        request = request?.newBuilder()?.addHeader("token", token)?.build()

        return chain.proceed(request)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val prefs = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)

        val email = prefs.getString("email", "") as String
        val password = prefs.getString("password", "") as String

        val respostaRetrofit = login.fazerLogin(Login(email, password)).execute()

        var token = respostaRetrofit.body()

        if(respostaRetrofit.isSuccessful && token != null) {
            val editor = prefs.edit()
            editor.putString("token", token.token)
            editor.apply()

            return response?.request()?.newBuilder()?.header("token", token.token)?.build()
        }

        return null
    }

}