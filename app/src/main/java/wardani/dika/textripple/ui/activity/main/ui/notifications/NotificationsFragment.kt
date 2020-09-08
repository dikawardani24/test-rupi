package wardani.dika.textripple.ui.activity.main.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.FragmentNotificationsBinding
import wardani.dika.textripple.model.Notification
import wardani.dika.textripple.ui.Injector
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.ui.adapter.NotificationItemAdapter
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.showError
import wardani.dika.textripple.util.startCallActivity
import wardani.dika.textripple.util.startEmailActivity

class NotificationsFragment : Fragment(), NotificationItemAdapter.OnActionSelectedListener {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notificationItemAdapter: NotificationItemAdapter
    private lateinit var notificationsViewModel: NotificationsViewModel

    private fun handle(it: LoadingState) {
        when(it) {
            LoadingState.LOADING -> {
                binding.noDataContainer.root.visibility = View.GONE
                binding.notificationRv.visibility = View.VISIBLE

                notificationItemAdapter.addItem(null)
                notificationItemAdapter.notifyDataSetChanged()
            }
            LoadingState.FINISH -> {
                notificationItemAdapter.remove(notificationItemAdapter.itemCount - 1)
                notificationItemAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun handle(it: Result<List<Notification>>?) {
        when(it) {
            is Result.Success -> {
                binding.noDataContainer.root.visibility = View.GONE
                binding.notificationRv.visibility = View.VISIBLE
                val data = it.data
                notificationItemAdapter.addItems(data)
                notificationItemAdapter.notifyDataSetChanged()
            }
            is Result.Failed -> {
                if (notificationItemAdapter.itemCount <= 0) {
                    binding.noDataContainer.root.visibility = View.VISIBLE
                    binding.notificationRv.visibility = View.GONE
                } else {
                    showError(requireContext(), it.error.message)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProviders.of(this, Injector.getNotificationViewModelFactory(requireContext()))
            .get(NotificationsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)

        notificationItemAdapter = NotificationItemAdapter()
        notificationItemAdapter.onActionSelectedListener = this

        binding.notificationRv.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationItemAdapter
        }

        notificationsViewModel.resultLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }

        notificationsViewModel.loadingStateLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        notificationsViewModel.loadNotifications()
    }
    override fun onCallSelected(notification: Notification) {
        startCallActivity(requireContext(), notification.user.phoneNumber, )
    }

    override fun onSendEmailSelected(notification: Notification) {
        startEmailActivity(requireContext(), notification.user.email, "Test Ripple", "Hi from Dika Wardani")
    }
}