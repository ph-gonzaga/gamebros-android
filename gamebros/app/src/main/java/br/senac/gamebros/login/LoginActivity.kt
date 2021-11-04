package br.senac.gamebros.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.senac.gamebros.R
import br.senac.gamebros.databinding.ActivityBottomNavigationBinding
import br.senac.gamebros.databinding.ActivityLoginBinding
import br.senac.gamebros.home.HomeFragment

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginUsuario = LoginUsuarioFragment()

        replaceFragment(loginUsuario)

    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerLogin, fragment).commit()
        }
    }
}