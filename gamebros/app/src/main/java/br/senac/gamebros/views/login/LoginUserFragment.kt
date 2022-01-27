package br.senac.gamebros.views.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import android.widget.Toast
import br.senac.gamebros.BottomNavigationActivity
import br.senac.gamebros.api.RetrofitInstance
import br.senac.gamebros.databinding.FragmentLoginUserBinding
import br.senac.gamebros.model.Login
import br.senac.gamebros.model.Token
import br.senac.gamebros.services.ARQUIVO_LOGIN
import br.senac.gamebros.utils.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginUserFragment : Fragment() {
    lateinit var binding: FragmentLoginUserBinding
    var loading = LoadingDialog(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginUserBinding.inflate(inflater)

        binding.btnEntrarUser.setOnClickListener {
//            val intent = Intent(activity, BottomNavigationActivity::class.java)
//            startActivity(intent)

            loading.startLoading()

            val email = binding.editFieldUserEmail.text.toString()
            val password = binding.editFieldSenhaEmail.text.toString()

            if(validaFormulario() == true){
                validaFormulario()

                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)
            } else {
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


                        }else{
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
        }

        binding.btnCadastarUser.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    LoginCreateFragment.newInstance()
                ).addToBackStack("fragLoginCreate").commit()
            }
        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginUserFragment()
    }

    private fun validaFormulario(): Boolean {

        var error = false

        if (binding.editFieldUserEmail.text.toString().isEmpty()) {
            binding.editFieldUserEmail.error = "E-mail ou CPF é obrigatório"
            error = true
        }
        if (binding.editFieldSenhaEmail.text.toString().isEmpty()) {
            binding.editFieldSenhaEmail.error = "A Senha é obrigatória"
            error = true
        }

        return error
    }

}