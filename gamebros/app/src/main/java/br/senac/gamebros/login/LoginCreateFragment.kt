package br.senac.gamebros.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.senac.gamebros.BottomNavigationActivity
import br.senac.gamebros.databinding.FragmentLoginCreateBinding

class LoginCreateFragment : Fragment() {
    lateinit var binding: FragmentLoginCreateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginCreateBinding.inflate(inflater)


        binding.btnCreateUser.setOnClickListener {
            val intent = Intent(activity, BottomNavigationActivity::class.java)
            startActivity(intent)
        }

//        binding.btnLoginUser.setOnClickListener {
//            container?.let {
//                parentFragmentManager.beginTransaction().replace(it.id,
//                    LoginUserFragment.newInstance()
//                ).addToBackStack("fragLoginAuth").commit()
//            }
//        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginCreateFragment()
    }
}