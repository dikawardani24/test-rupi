package wardani.dika.textripple.repository.product

import wardani.dika.textripple.model.Product
import wardani.dika.textripple.util.Result

interface ProductRepository {
    suspend fun loadProducts(): Result<List<Product>>
    suspend fun initiateDataProduct(): Result<Unit>
}