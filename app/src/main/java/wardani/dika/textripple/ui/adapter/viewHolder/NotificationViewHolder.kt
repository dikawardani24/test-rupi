package wardani.dika.textripple.ui.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import wardani.dika.textripple.databinding.ItemNotificationBinding
import wardani.dika.textripple.model.Notification

class NotificationViewHolder (val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(notification: Notification) {
        binding.notification = notification
    }
}
