package wardani.dika.textripple.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass


fun AppCompatActivity.startActivity(kClass: KClass<*>, block: Intent.() -> Unit = {}) {
    val intent = Intent(this, kClass.java)
    block(intent)
    startActivity(intent)
}

fun AppCompatActivity.showWarning(message: String?) {
    showWarning(this, message)
}
fun AppCompatActivity.showError(message: String?) {
    showError(this, message)
}
fun AppCompatActivity.showSuccess(message: String?) {
    showSuccess(this, message)
}

fun startEmailActivity(context: Context, recipient: String?, subject: String?, body: String?) {
    val mailto = java.lang.String.format(
        "mailto:%s?subject=%s&body=%s",
        recipient,
        Uri.encode(subject),
        Uri.encode(body)
    )
    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.data = Uri.parse(mailto)
    context.startActivity(emailIntent)
}

fun startCallActivity(context: Context, phoneNumber: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
            return
        }
    }
    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
    context.startActivity(intent)
}