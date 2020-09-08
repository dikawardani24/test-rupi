package wardani.dika.textripple.ui.activity.main.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.FragmentDashboardBinding
import wardani.dika.textripple.model.User
import wardani.dika.textripple.ui.Injector
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.ui.activity.main.MainActivity
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.showError

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    private fun handle(it: LoadingState) {
        when(it) {
            LoadingState.LOADING -> {
                binding.run {
                    loadingContainer.root.visibility = View.VISIBLE
                }
            }
            LoadingState.FINISH -> {
                binding.run {
                    loadingContainer.root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun handle(it: Result<User>) {
        when(it) {
            is Result.Success -> {
                binding.run {
                    profileContainer.root.visibility = View.VISIBLE
                    noDataContainer.root.visibility = View.GONE
                    profileContainer.user = it.data
                }
            }
            is Result.Failed -> {
                binding.run {
                    profileContainer.root.visibility = View.GONE
                    noDataContainer.root.visibility = View.VISIBLE
                    profileContainer.user = null
                }

                showError(requireContext(), it.error.message)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this, Injector.getDashboardViewModelFactory(requireActivity()))
            .get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        dashboardViewModel.loadingStateLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }

        dashboardViewModel.resultLiveData.observe(viewLifecycleOwner) {
            handle(it)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.run {
            val username = intent.getStringExtra(MainActivity.USER_KEY)
            dashboardViewModel.loadProfile(username)
        }
    }
}