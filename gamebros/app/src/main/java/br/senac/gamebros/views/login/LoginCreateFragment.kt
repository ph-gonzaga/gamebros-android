package br.senac.gamebros.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.senac.gamebros.BottomNavigationActivity
import br.senac.gamebros.api.RetrofitInstance
import br.senac.gamebros.databinding.FragmentLoginCreateBinding
import br.senac.gamebros.model.*
import br.senac.gamebros.services.ARQUIVO_LOGIN
import br.senac.gamebros.services.OrderService
import br.senac.gamebros.services.UserService
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.utils.LoadingDialog
import br.senac.gamebros.views.orders.OrderListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginCreateFragment : Fragment() {
    lateinit var binding: FragmentLoginCreateBinding
    var loading = LoadingDialog(this)
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginCreateBinding.inflate(inflater)


        binding.btnCreateUser.setOnClickListener {
            if(validaFormulario() == true){
                validaFormulario()
            } else {
                val request = UserCreateRequest(
                    name = binding.editFieldCreateUserNome.text.toString(),
                    email = binding.editFieldCreateUserEmail.text.toString(),
                    password = binding.editFieldCreateUserPass.text.toString(),
                    cpf = binding.editFieldCreateUserCPF.text.toString(),
                    phone = binding.editFieldCreateUserTel.text.toString()
                )

                addUser(request)
            }
        }


        return binding.root

    }

    private fun addUser(request: UserCreateRequest) {
        val service = retrofit.create(UserService::class.java)
        val call = service.criarUsuario(request)
        loading.startLoading()

        val callback = object : Callback<UserCreateResponse> {
            override fun onResponse(call: Call<UserCreateResponse>, response: Response<UserCreateResponse>){
                if(response.isSuccessful){
                    Log.i("callback", response.body().toString())



                    login(request.email, request.password)
                } else {
                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<UserCreateResponse>, t: Throwable) {
                Log.e("ERROR", "Falha ao executar serviço", t)

                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)
            }
        }

        call.enqueue(callback)
    }

    private fun login(email: String, password: String){
        val callback = object: Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                val responseLogin = response.body()

                Log.e("RESPONSE", responseLogin.toString())

                if (responseLogin?.token != null){
                    val editor = activity!!.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE).edit()
                    editor.putInt("id", responseLogin?.id)
                    editor.putString("token", responseLogin?.token)
                    editor.putString("name", responseLogin?.name)
                    editor.putString("phone", responseLogin?.phone)
                    editor.putString("email", email)
                    editor.putString("password", password)

                    editor.apply()

                    Toast.makeText(activity, "Login efetuado com sucesso.", Toast.LENGTH_LONG).show()

                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    val intent = Intent(activity, BottomNavigationActivity::class.java)
                    startActivityForResult(intent,1)


                } else {
                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    var msg =  "Não foi possivel efetuar o login."
                    Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("LoginActivity", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)

                Toast.makeText(activity,"", Toast.LENGTH_LONG).show()
                Log.e("LoginActivity","onCreate",t)
            }

        }
        RetrofitInstance.login.fazerLogin(Login(email, password)).enqueue(callback)
    }

    private fun validaFormulario(): Boolean {
        var error = false

        if (binding.editFieldCreateUserEmail.text.toString().isEmpty()) {
            binding.editFieldCreateUserEmail.error = "E-mail é obrigatório"
            error = true
        }
        if (binding.editFieldCreateUserNome.text.toString().isEmpty()) {
            binding.editFieldCreateUserNome.error = "Nome é obrigatório"
            error = true
        }
        if (binding.editFieldCreateUserCPF.text.toString().isEmpty()) {
            binding.editFieldCreateUserCPF.error = "CPF é obrigatório"
            error = true
        }
        if (binding.editFieldCreateUserTel.text.toString().isEmpty()) {
            binding.editFieldCreateUserTel.error = "Telefone é obrigatório"
            error = true
        }
        if (binding.editFieldCreateUserPass.text.toString().isEmpty()) {
            binding.editFieldCreateUserPass.error = "Senha é obrigatório"
            error = true
        }

        return error
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginCreateFragment()
    }
}