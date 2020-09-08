package wardani.dika.textripple.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.ItemNotificationBinding
import wardani.dika.textripple.model.Notification
import wardani.dika.textripple.ui.adapter.viewHolder.NotificationViewHolder

class NotificationItemAdapter: PagingAdapter<Notification>() {
    var onActionSelectedListener: OnActionSelectedListener? = null

    interface OnActionSelectedListener {
        fun onCallSelected(notification: Notification)
        fun onSendEmailSelected(notification: Notification)
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemNotificationBinding>(inflater, R.layout.item_notification, parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(data: Notification, holder: RecyclerView.ViewHolder) {
        if (holder is NotificationViewHolder) {
            holder.bind(data)

            onActionSelectedListener?.let {
                holder.binding.run {
                    callBtn.setOnClickListener { _ ->
                        it.onCallSelected(data)
                    }
                    sendEmailBtn.setOnClickListener {_ ->
                        it.onSendEmailSelected(data)
                    }
                }
            }
        }
    }

}