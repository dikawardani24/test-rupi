package wardani.dika.textripple.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.ItemProductBinding
import wardani.dika.textripple.model.Product
import wardani.dika.textripple.ui.adapter.viewHolder.ProductViewHolder

class ProductItemAdapter: PagingAdapter<Product>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProductBinding>(inflater, R.layout.item_product, parent, false)
        return ProductViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(data: Product, holder: RecyclerView.ViewHolder) {
        if (holder is ProductViewHolder) {
            holder.bind(data)
        }
    }
}