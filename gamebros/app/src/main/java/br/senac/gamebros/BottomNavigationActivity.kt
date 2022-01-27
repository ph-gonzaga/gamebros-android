package br.senac.gamebros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import br.senac.gamebros.databinding.ActivityBottomNavigationBinding
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import br.senac.gamebros.model.Cart
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.services.CartsService
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.views.account.AccountFragment
import br.senac.gamebros.views.cart.CartEmptyFragment
import br.senac.gamebros.views.cart.CartFragment
import br.senac.gamebros.views.categories.CategoriesFragment
import br.senac.gamebros.views.home.HomeFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BottomNavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomNavigationBinding

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = HomeFragment()
        val categorias = CategoriesFragment()
        var conta = AccountFragment()

        replaceFragment(home)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(home)
                R.id.categoria -> replaceFragment(categorias)
                R.id.qrcode -> openQrCodeActivity()
                R.id.conta -> replaceFragment(conta)
                R.id.sacola -> buscaProdutosCarrinho() //replaceFragment(sacolaVazia)
                else -> "Invalid "
            }

            true
        }
    }

    private fun openQrCodeActivity() {
        val i = Intent(this, QrCodeActivity::class.java)
        startActivityForResult(i, 1)
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).commit()
        }
    }

    private fun buscaProdutosCarrinho(){
        // mostra fragmento default
        replaceFragment(CartEmptyFragment())

        val service = retrofit.create(CartsService::class.java)
        //user_id
        val call = service.listarProdutosCarrinho(1)

        val callback = object : Callback<Cart> {
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                if(response.isSuccessful){
                    val listaProdutos = response.body()
                    Log.i("Total", listaProdutos?.totalCarrinho?.toInt().toString())
                    if(listaProdutos?.totalCarrinho?.toInt()!! > 0){
                        val bundle = Bundle()
                        val list = ArrayList<CartProductsResponse>(listaProdutos?.produtos)

                        bundle.putSerializable("data", list)
                        bundle.putFloat("totalCart", listaProdutos?.totalCarrinho)

                        val fragment = CartFragment()
                        fragment.arguments = bundle

                        replaceFragment(fragment)
                    }

                    Log.i("Listagem de produtos - cart", listaProdutos.toString())

                } else{
                    Snackbar.make(
                        binding.container,
                        "Não é possível atualizar produtos",
                        Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Snackbar.make(
                    binding.container,
                    "Não é possível se conectar ao servidor",
                    Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)
    }


}
