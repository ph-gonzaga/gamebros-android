package br.senac.gamebros.views.products

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.senac.gamebros.adapter.ProductAdapter
import br.senac.gamebros.databinding.FragmentListProductBinding
import br.senac.gamebros.repository.Repository


class ListProductFragment : Fragment() {
    private var _binding: FragmentListProductBinding? = null
    private val binding get() = _binding!!
    private val listProductViewModel: ListProductViewModel by activityViewModels {
        ListProductViewModelFactory(Repository())
    }
    private var adapter = ProductAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listProductViewModel.listarProdutos()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentListProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listProductViewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if(response.isSuccessful){
                adapter.setProducts(response.body()!!)
                Log.d("Result", response.body()!!.toString())
            } else {
                Log.d("ResultError", response.code().toString())
            }
        })

        binding.apply {
            recycleListProducts.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recycleListProducts.adapter = adapter
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