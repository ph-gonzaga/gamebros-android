package br.senac.gamebros

import androidx.appcompat.app.AppCompatActivity
import br.senac.gamebros.databinding.ActivityBottomNavigationBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.senac.gamebros.account.AccountFragment
import br.senac.gamebros.categories.CategoriesFragment
import br.senac.gamebros.home.HomeFragment

class BottomNavigationActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = HomeFragment()
        val categorias = CategoriesFragment()
        var account = AccountFragment()

        replaceFragment(home)

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> replaceFragment(home)
                R.id.categorias -> replaceFragment(categorias)
                R.id.conta -> replaceFragment(account)
                else -> "Invalid "
            }

            true

            }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).commit()
        }
    }

}