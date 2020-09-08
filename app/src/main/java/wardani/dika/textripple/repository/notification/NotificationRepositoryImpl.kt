package wardani.dika.textripple.repository.notification

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import wardani.dika.textripple.model.Notification
import wardani.dika.textripple.model.User
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.fromJson
import wardani.dika.textripple.util.toJson
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class NotificationRepositoryImpl(private val sharedPreferences: SharedPreferences) : NotificationRepository {

    override suspend fun getNotifications(): Result<List<Notification>> {
        val notifications = ArrayList<Notification>()

        return try {
            val json = sharedPreferences.getString(NOTIFICATION_KEY, null)
            if (json != null) {
                val type = object : TypeToken<List<Notification>>() {}.type
                val savedNotifications = type.fromJson<List<Notification>>(json)
                notifications.addAll(savedNotifications)
            }

            for (i in 0 until 10) {
                val newNotification: Notification
                if (i % 2 == 0) {
                    newNotification = Notification(
                        id = UUID.randomUUID(),
                        date = Calendar.getInstance().time,
                        user = User(
                            name = "Dika Wardani",
                            password = "",
                            email = "dikawardani24@gmail.com",
                            phoneNumber = "",
                            address = "",
                            city = ""
                        ),
                        message = "No day without reading, no day without structuring the logic and no day without digital then no day without code"
                    )
                } else if (i % 3 == 0) {
                    newNotification = Notification(
                        id = UUID.randomUUID(),
                        date = Calendar.getInstance().time,
                        user = User(
                            name = "Arni Setiyani",
                            password = "",
                            email = "arni.tiyani@gmail.com",
                            phoneNumber = "",
                            address = "",
                            city = ""
                        ),
                        message = "Photography is my hobby, social marketing is my job. It so visualize and i loved it"
                    )
                } else {
                    newNotification = Notification(
                        id = UUID.randomUUID(),
                        date = Calendar.getInstance().time,
                        user = User(
                            name = "Januar",
                            password = "",
                            email = "januar@gmail.com",
                            phoneNumber = "",
                            address = "",
                            city = ""
                        ),
                        message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                    )
                }

                notifications.add(newNotification)
            }

            val newJson = notifications.toJson()
            sharedPreferences.edit()
                .putString(NOTIFICATION_KEY, newJson)
                .apply()
            Result.Success(notifications)
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }

    companion object {
        private const val NOTIFICATION_KEY = "notification"
    }
}