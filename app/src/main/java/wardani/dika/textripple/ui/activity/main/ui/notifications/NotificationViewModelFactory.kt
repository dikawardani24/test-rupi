package wardani.dika.textripple.ui.activity.main.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wardani.dika.textripple.repository.notification.NotificationRepository

class NotificationViewModelFactory(
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return NotificationsViewModel(notificationRepository) as T
    }

}