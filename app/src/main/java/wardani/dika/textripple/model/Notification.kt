package wardani.dika.textripple.model

import java.util.*

data class Notification(
    var id: UUID,
    var date: Date,
    var user: User,
    var message: String
)