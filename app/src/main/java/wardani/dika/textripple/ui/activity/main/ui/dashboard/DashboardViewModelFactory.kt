package wardani.dika.textripple.ui.activity.main.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wardani.dika.textripple.repository.user.UserRepository

class DashboardViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DashboardViewModel(userRepository) as T
    }

}