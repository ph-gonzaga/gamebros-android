package br.senac.gamebros.views.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentAccountBinding
import br.senac.gamebros.views.login.LoginActivity
import br.senac.gamebros.views.orders.OrderListFragment

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater)

        binding.btnMeusPedidos.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    OrderListFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnCadastro.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    SignupFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnEndereco.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    AddressFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnSair.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}