package wardani.dika.textripple.repository

import android.content.Context
import wardani.dika.textripple.repository.notification.NotificationRepository
import wardani.dika.textripple.repository.notification.NotificationRepositoryImpl
import wardani.dika.textripple.repository.product.ProductRepository
import wardani.dika.textripple.repository.product.ProductRepositoryImp
import wardani.dika.textripple.repository.user.UserRepository
import wardani.dika.textripple.repository.user.UserRepositoryImpl

object RepositoryFactory  {
    private const val SHARED_PREF_NAME = "wardani.dika.textripple"

    fun createUserRepository(context: Context): UserRepository {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return UserRepositoryImpl(sharedPreferences)
    }

    fun createProductRepository(context: Context): ProductRepository {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return ProductRepositoryImp(sharedPreferences)
    }

    fun createNotificationRepository(context: Context): NotificationRepository {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return NotificationRepositoryImpl(sharedPreferences)
    }
}