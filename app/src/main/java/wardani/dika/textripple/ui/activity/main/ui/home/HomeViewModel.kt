package wardani.dika.textripple.ui.activity.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wardani.dika.textripple.model.Product
import wardani.dika.textripple.repository.product.ProductRepository
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.util.Result

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    val loadingStateLiveData: LiveData<LoadingState> = MutableLiveData()
    val resultLiveData: LiveData<Result<List<Product>>> = MutableLiveData()

    fun loadProducts() {
        val loadingStateLiveData = loadingStateLiveData as MutableLiveData
        val resultLiveData = resultLiveData as MutableLiveData

        loadingStateLiveData.postValue(LoadingState.LOADING)
        viewModelScope.launch {
            when(val initResult = productRepository.initiateDataProduct()) {
                is Result.Success -> {
                    val result = productRepository.loadProducts()

                    loadingStateLiveData.postValue(LoadingState.FINISH)

                    when(result) {
                        is Result.Success -> {
                            val data = result.data
                            resultLiveData.postValue(Result.Success(data))
                        }
                        is Result.Failed -> {
                            val error = result.error
                            resultLiveData.postValue(Result.Failed(error))
                        }
                    }
                }
                is Result.Failed -> {
                    loadingStateLiveData.postValue(LoadingState.FINISH)
                    val error = initResult.error
                    resultLiveData.postValue(Result.Failed(error))
                }
            }

        }
    }
}