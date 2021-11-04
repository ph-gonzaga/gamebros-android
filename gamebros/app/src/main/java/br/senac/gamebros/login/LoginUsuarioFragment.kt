package br.senac.gamebros.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.R
import br.senac.gamebros.databinding.FragmentLoginUsuarioBinding

class LoginUsuarioFragment : Fragment() {
    lateinit var binding: FragmentLoginUsuarioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginUsuarioBinding.inflate(inflater)



        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginUsuarioFragment()
    }
}