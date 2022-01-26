package br.senac.gamebros.views.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.senac.gamebros.LoginActivityTest
import br.senac.gamebros.R
import br.senac.gamebros.services.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val shared = SharedPrefManager.getInstance(requireContext())

        if(!shared.isLoggedIn){
            val i = Intent(context, LoginActivityTest::class.java)
            startActivity(i)
        } else {
            editFieldNomeConta.setText(shared.user.email)
            editFieldEmailConta.setText(shared.user.name) //invertido mesmo
            editFieldNumero.setText(shared.user.phone)

        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}