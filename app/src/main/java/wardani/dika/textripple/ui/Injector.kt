package wardani.dika.textripple.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import wardani.dika.textripple.repository.RepositoryFactory
import wardani.dika.textripple.ui.activity.main.ui.dashboard.DashboardViewModelFactory
import wardani.dika.textripple.ui.activity.main.ui.home.HomeViewModelFactory
import wardani.dika.textripple.ui.activity.main.ui.notifications.NotificationViewModelFactory
import wardani.dika.textripple.ui.activity.register.RegisterViewModelFactory

object Injector {

    fun getDashboardViewModelFactory(context: Context): ViewModelProvider.Factory {
        return DashboardViewModelFactory(
            userRepository = RepositoryFactory.createUserRepository(context)
        )
    }

    fun getHomeViewModelFactory(context: Context): ViewModelProvider.Factory {
        return HomeViewModelFactory(
            productRepository = RepositoryFactory.createProductRepository(context)
        )
    }

    fun getRegisterViewModelFactory(context: Context): ViewModelProvider.Factory {
        return RegisterViewModelFactory(
            userRepository = RepositoryFactory.createUserRepository(context)
        )
    }

    fun getNotificationViewModelFactory(context: Context): ViewModelProvider.Factory {
        return NotificationViewModelFactory(
            notificationRepository = RepositoryFactory.createNotificationRepository(context)
        )
    }

}