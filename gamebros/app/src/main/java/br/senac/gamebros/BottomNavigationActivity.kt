package br.senac.gamebros

import androidx.appcompat.app.AppCompatActivity
import br.senac.gamebros.databinding.ActivityBottomNavigationBinding
import android.os.Bundle
import androidx.fragment.app.Fragment

class BottomNavigationActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = HomeFragment()
        val categorias = CategoriasFragment()

        replaceFragment(home)
        
        binding.bottomNavigation.setOnItemReselectedListener {

            when (it.itemId) {
                R.id.home -> replaceFragment(home)
                R.id.categorias -> replaceFragment(categorias)
                else -> "Invalid "
            }

            true

            }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment).commit()
        }
    }

}