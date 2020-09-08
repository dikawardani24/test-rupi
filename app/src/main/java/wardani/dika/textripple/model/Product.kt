package wardani.dika.textripple.model

import java.util.*

data class Product(
    var id: UUID,
    var name: String,
    var price: Double,
    var category: ProductCategory
)