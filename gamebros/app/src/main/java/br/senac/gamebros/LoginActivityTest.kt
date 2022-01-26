package br.senac.gamebros

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.senac.gamebros.api.RetrofitInstance.login
import br.senac.gamebros.databinding.ActivityLoginTestBinding
import br.senac.gamebros.model.Login
import br.senac.gamebros.model.Token
import br.senac.gamebros.services.ARQUIVO_LOGIN
import br.senac.gamebros.views.account.SignupFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityTest : AppCompatActivity() {
    lateinit var binding: ActivityLoginTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrarUser.setOnClickListener {
            val email = binding.editFieldUserEmail.text.toString()
            val password = binding.editFieldSenhaEmail.text.toString()


            val callback = object: Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    val responseLogin = response.body()

                    Log.e("RESPONSE", responseLogin.toString())

                    if (responseLogin?.token != null){
                        val editor = getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE).edit()
                        editor.putInt("id", responseLogin?.id)
                        editor.putString("token", responseLogin?.token)
                        editor.putString("name", responseLogin?.name)
                        editor.putString("phone", responseLogin?.phone)
                        editor.putString("email", email)
                        editor.putString("password", password)

                        editor.apply()

                        Toast.makeText(this@LoginActivityTest, "Login efetuado com sucesso.", Toast.LENGTH_LONG).show()

                        val i = Intent(this@LoginActivityTest, BottomNavigationActivity::class.java)
                        startActivityForResult(i,1)

                    }else{
                        var msg =  "Não foi possivel efetuar o login."
                        Toast.makeText(this@LoginActivityTest, msg, Toast.LENGTH_LONG).show()
                        response.errorBody()?.let {
                            Log.e("LoginActivity", it.string())
                        }
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(this@LoginActivityTest,"", Toast.LENGTH_LONG).show()
                    Log.e("LoginActivity","onCreate",t)
                }

            }


            login.fazerLogin(Login(email, password)).enqueue(callback)
        }

        binding.btnCadastarUser.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                SignupFragment.newInstance(),
            ).addToBackStack("fragSignup").commit()
        }
    }
}