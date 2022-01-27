package br.senac.gamebros.views.products

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.senac.gamebros.adapter.ProductAdapter
import br.senac.gamebros.databinding.FragmentListProductBinding
import br.senac.gamebros.repository.Repository
import br.senac.gamebros.utils.LoadingDialog


class ListProductFragment : Fragment() {
    private var _binding: FragmentListProductBinding? = null
    private val binding get() = _binding!!
    private var categoryIdBundle = 0

    private val listProductViewModel: ListProductViewModel by activityViewModels {
        Log.i("listProductViewModel", "rodei")
        Log.i("categoryIdBundle|listProductViewModel", categoryIdBundle.toString())

        ListProductViewModelFactory(Repository(), categoryIdBundle)
    }

    private var adapter = ProductAdapter {
        Log.i("adapter", "rodei")
        Log.e("Recebido", it.id.toString())
    }

    override fun onAttach(context: Context) {
        Log.i("onAttach", "rodei")

        val bundle = arguments
        val arg = bundle?.getInt("categoryId")

        if (arg != null) {
            categoryIdBundle = arg
        }

        Log.i("categoryIdBundle", categoryIdBundle.toString())

        super.onAttach(context)
        listProductViewModel.listarProdutos(categoryIdBundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentListProductBinding.inflate(inflater, container, false)
        Log.i("onCreateView", "rodei")

        val bundle = arguments
        val args = bundle?.getInt("categoryId")?: 0
        categoryIdBundle = args


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = LoadingDialog(this)
        loading.startLoading()

        Log.i("onViewCreated", "rodei")

        listProductViewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if(response.isSuccessful){
                adapter.setProducts(response.body()!!)
                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)
                Log.d("Result", response.body()!!.toString())
            } else {
                Log.d("ResultError", response.code().toString())
            }
        })

        binding.apply {
            recyclerListProducts.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recyclerListProducts.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListProductFragment()
    }
}