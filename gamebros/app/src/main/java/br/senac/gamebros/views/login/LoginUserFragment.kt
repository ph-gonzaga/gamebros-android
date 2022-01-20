package br.senac.gamebros.views.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import br.senac.gamebros.BottomNavigationActivity
import br.senac.gamebros.databinding.FragmentLoginUserBinding


class LoginUserFragment : Fragment() {
    lateinit var binding: FragmentLoginUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginUserBinding.inflate(inflater)

        binding.btnEntrarUser.setOnClickListener {
            val intent = Intent(activity, BottomNavigationActivity::class.java)
            startActivity(intent)
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
}