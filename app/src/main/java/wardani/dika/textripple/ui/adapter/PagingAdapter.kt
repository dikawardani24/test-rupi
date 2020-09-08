package wardani.dika.textripple.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.ItemLoadMoreBinding
import wardani.dika.textripple.ui.adapter.constant.ViewType
import wardani.dika.textripple.ui.adapter.viewHolder.LoadMoreViewHolder

abstract class PagingAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList = ArrayList<T?>()

    protected abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder
    protected abstract fun onBindViewHolder(data: T, holder: RecyclerView.ViewHolder)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(ViewType.toViewType(viewType)) {
            ViewType.DATA -> onCreateViewHolder(inflater, parent)
            ViewType.LOADING_MORE -> {
                val binding = DataBindingUtil.inflate<ItemLoadMoreBinding>(inflater, R.layout.item_load_more, parent, false)
                LoadMoreViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if (data != null) {
            onBindViewHolder(data, holder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = dataList[position]
        return if (data != null) {
            ViewType.DATA.value
        } else {
            ViewType.LOADING_MORE.value
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addItem(item: T?) {
        dataList.add(item)
    }

    fun addItems(items: List<T?>) {
        dataList.addAll(items)
    }

    fun remove(position: Int) {
        if (position > -1) {
            dataList.removeAt(position)
        }
    }
}