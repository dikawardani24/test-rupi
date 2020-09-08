package wardani.dika.textripple.ui.adapter.viewHolder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.ItemProductBinding
import wardani.dika.textripple.model.Product
import wardani.dika.textripple.model.ProductCategory

class ProductViewHolder(private val context: Context,
                        private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product

        val colorId = when(product.category) {
            ProductCategory.ELECTRONIC -> R.color.electronic
            ProductCategory.GADGET -> R.color.gadget
            ProductCategory.VEHICLE -> R.color.vehicle
        }

        val color = ContextCompat.getColor(context, colorId)
        binding.categoryTv.setTextColor(color)
    }
}
