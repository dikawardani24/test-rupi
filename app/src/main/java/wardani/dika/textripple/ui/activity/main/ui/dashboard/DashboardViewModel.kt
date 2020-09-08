package wardani.dika.textripple.ui.activity.main.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wardani.dika.textripple.model.User
import wardani.dika.textripple.repository.user.UserRepository
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.util.Result

class DashboardViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val loadingStateLiveData: LiveData<LoadingState> = MutableLiveData()
    val resultLiveData: LiveData<Result<User>> = MutableLiveData()

    fun loadProfile(username: String?) {
        val resultLiveData= resultLiveData as MutableLiveData

        if (username == null) {
            resultLiveData.postValue(Result.Failed(Exception("No data username has been received")))
            return
        }

        val loadingLiveData = loadingStateLiveData as MutableLiveData
        loadingLiveData.postValue(LoadingState.LOADING)

        viewModelScope.launch {
            when(val result = userRepository.getDetail(username)) {
                is Result.Success -> {
                    val data = result.data
                    resultLiveData.postValue(Result.Success(data))
                }
                is Result.Failed -> {
                    val error = result.error
                    resultLiveData.postValue(Result.Failed(error))
                }
            }

            loadingLiveData.postValue(LoadingState.FINISH)
        }
    }
}