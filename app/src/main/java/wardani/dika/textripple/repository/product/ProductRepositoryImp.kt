package wardani.dika.textripple.repository.product

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import wardani.dika.textripple.exception.NoDataException
import wardani.dika.textripple.model.Product
import wardani.dika.textripple.model.ProductCategory
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.fromJson
import wardani.dika.textripple.util.toJson
import java.util.*

class ProductRepositoryImp(
    private val sharedPreferences: SharedPreferences
) : ProductRepository {

    override suspend fun loadProducts(): Result<List<Product>> {
        val jsonData = sharedPreferences.getString(PRODUCT_KEY, null)

        return if (jsonData != null) {
            val type = object : TypeToken<List<Product>>() {}.type
            val products = type.fromJson<List<Product>>(jsonData)
            Result.Success(products)
        } else {
            Result.Failed(NoDataException("Data product not available"))
        }
    }

    override suspend fun initiateDataProduct(): Result<Unit> {
        val newProducts = arrayListOf<Product>()

        for (i in 0 until 10) {
            val newProduct: Product
            when {
                i % 2 == 0 -> {
                    newProduct = Product(
                        id = UUID.randomUUID(),
                        name = "Asus Zenfone 5",
                        category = ProductCategory.GADGET,
                        price = 1550000.0
                    )
                }
                i % 3 == 0 -> {
                    newProduct = Product(
                        id = UUID.randomUUID(),
                        name = "Apple TV",
                        category = ProductCategory.ELECTRONIC,
                        price = 15500000.0
                    )
                }
                else -> {
                    newProduct = Product(
                        id = UUID.randomUUID(),
                        name = "Daihatsu Sigra",
                        category = ProductCategory.VEHICLE,
                        price = 1550000.0
                    )
                }
            }

            newProducts.add(newProduct)
        }

        return try {
            val json = newProducts.toJson()
            sharedPreferences.edit()
                .putString(PRODUCT_KEY, json)
                .apply()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }

    companion object {
        private const val PRODUCT_KEY = "product"
    }
}