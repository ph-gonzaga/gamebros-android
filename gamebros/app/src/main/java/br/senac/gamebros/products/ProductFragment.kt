package br.senac.gamebros.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.R
import br.senac.gamebros.account.AccountFragment
import br.senac.gamebros.account.AddressFragment
import br.senac.gamebros.account.SignupFragment
import br.senac.gamebros.databinding.FragmentAccountBinding
import br.senac.gamebros.databinding.FragmentProductBinding


class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater)

        binding.btnComprarItem.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    SignupFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}