package wardani.dika.textripple.ui.activity.main.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wardani.dika.textripple.model.Notification
import wardani.dika.textripple.repository.notification.NotificationRepository
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.util.Result

class NotificationsViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val loadingStateLiveData: LiveData<LoadingState> = MutableLiveData()
    val resultLiveData: LiveData<Result<List<Notification>>> = MutableLiveData()

    fun loadNotifications() {
        val loadingStateLiveData = loadingStateLiveData as MutableLiveData
        val resultLiveData = resultLiveData as MutableLiveData

        loadingStateLiveData.postValue(LoadingState.LOADING)
        viewModelScope.launch {
            val result = notificationRepository.getNotifications()

            loadingStateLiveData.postValue(LoadingState.FINISH)

            when(result) {
                is Result.Success -> {
                    val data = result.data
                    resultLiveData.postValue(Result.Success(data))
                }
                is Result.Failed -> {
                    val error = result.error
                    resultLiveData.postValue(Result.Failed(error))
                }
            }

        }
    }
}