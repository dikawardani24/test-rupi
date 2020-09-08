package wardani.dika.textripple.ui.activity.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.FragmentHomeBinding
import wardani.dika.textripple.model.Product
import wardani.dika.textripple.ui.Injector
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.ui.adapter.ProductItemAdapter
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.showError

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productItemAdapter: ProductItemAdapter
    private lateinit var homeViewModel: HomeViewModel

    private fun handle(it: LoadingState) {
        when(it) {
            LoadingState.LOADING -> {
                binding.noDataContainer.root.visibility = View.GONE
                binding.productRv.visibility = View.VISIBLE

                productItemAdapter.addItem(null)
                productItemAdapter.notifyDataSetChanged()
            }
            LoadingState.FINISH -> {
                productItemAdapter.remove(productItemAdapter.itemCount - 1)
                productItemAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun handle(it: Result<List<Product>>) {
        when(it) {
            is Result.Success -> {
                binding.noDataContainer.root.visibility = View.GONE
                binding.productRv.visibility = View.VISIBLE
                val data = it.data
                productItemAdapter.addItems(data)
                productItemAdapter.notifyDataSetChanged()
            }
            is Result.Failed -> {
                if (productItemAdapter.itemCount <= 0) {
                    binding.noDataContainer.root.visibility = View.VISIBLE
                    binding.productRv.visibility = View.GONE
                } else {
                    showError(requireContext(), it.error.message)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, Injector.getHomeViewModelFactory(requireContext()))
            .get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        productItemAdapter = ProductItemAdapter()
        binding.productRv.run {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productItemAdapter
        }

        homeViewModel.resultLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }

        homeViewModel.loadingStateLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.loadProducts()
    }
}