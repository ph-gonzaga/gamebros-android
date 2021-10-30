package br.senac.gamebros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater)

        binding.btnCadastro.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,SignupFragment.newInstance()).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnEndereco.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,AddressFragment.newInstance()).addToBackStack("fragAccount").commit()
            }
        }
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}