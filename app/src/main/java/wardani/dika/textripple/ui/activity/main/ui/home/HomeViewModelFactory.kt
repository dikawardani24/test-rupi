package wardani.dika.textripple.ui.activity.main.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wardani.dika.textripple.repository.product.ProductRepository

class HomeViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(productRepository) as T
    }

}