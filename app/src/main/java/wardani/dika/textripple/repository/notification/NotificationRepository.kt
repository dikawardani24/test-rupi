package wardani.dika.textripple.repository.notification

import wardani.dika.textripple.model.Notification
import wardani.dika.textripple.util.Result

interface NotificationRepository {
    suspend fun getNotifications(): Result<List<Notification>>
}