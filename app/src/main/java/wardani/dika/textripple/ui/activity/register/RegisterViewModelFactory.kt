package wardani.dika.textripple.ui.activity.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wardani.dika.textripple.repository.user.UserRepository

class RegisterViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RegisterViewModel(userRepository) as T
    }

}