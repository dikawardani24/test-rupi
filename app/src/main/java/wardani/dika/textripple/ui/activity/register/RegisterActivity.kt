package wardani.dika.textripple.ui.activity.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import wardani.dika.textripple.R
import wardani.dika.textripple.databinding.ActivityRegisterBinding
import wardani.dika.textripple.exception.ValidationException
import wardani.dika.textripple.model.User
import wardani.dika.textripple.ui.Injector
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.ui.activity.main.MainActivity
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.showError
import wardani.dika.textripple.util.startActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var allReadyWatchedChange: Boolean = false

    private fun watchInputChange() {
        if (allReadyWatchedChange) {
            return
        }

        allReadyWatchedChange = true
        binding.run {
            nameEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            passwordEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            confirmPasswordEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            phoneEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            emailEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            addressEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
            cityEt.addTextChangedListener {
                viewModel.validate(user, confirmPassword)
            }
        }
    }

    private fun handle(it: Result<Unit>) {
        when(it) {
            is Result.Success -> {
                startActivity(MainActivity::class) {
                    putExtra(MainActivity.USER_KEY, binding.user?.email)
                }
            }
            is Result.Failed -> {
                val error = it.error

                if(error is ValidationException) {
                    watchInputChange()
                }

                showError(it.error.message)
            }
        }
    }

    private fun handle(it: ValidationResult) {
        it.fieldResults.forEach {
           binding.run {
               val input = when(it.key) {
                   DataKey.NAME -> nameEt
                   DataKey.PASSWORD -> passwordEt
                   DataKey.CONFIRM_PASSWORD -> confirmPasswordEt
                   DataKey.EMAIL -> emailEt
                   DataKey.PHONE -> phoneEt
                   DataKey.ADDRESS -> addressEt
                   DataKey.CITY -> cityEt
               }

               input.error = it.message
           }
        }
    }

    private fun handle(it: LoadingState) {
        binding.run {
            when(it) {
                LoadingState.LOADING -> {
                    actionBtnContainer.visibility = View.GONE
                    progressAction.visibility = View.VISIBLE
                }
                LoadingState.FINISH -> {
                    actionBtnContainer.visibility = View.VISIBLE
                    progressAction.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, Injector.getRegisterViewModelFactory(this))
            .get(RegisterViewModel::class.java)

        binding.user = User(
            name = "",
            password = "",
            email = "",
            phoneNumber = "",
            address = "",
            city = ""
        )
        binding.confirmPassword = ""

        binding.registerBtn.setOnClickListener {
            val user = binding.user
            val confirmPassword = binding.confirmPassword

            if (user != null && confirmPassword != null) {
                viewModel.save(user, confirmPassword)
            } else {
                showError("No data user has been attached")
            }
        }

        binding.resetBtn.setOnClickListener {
            binding.run {
                val inputs = listOf(nameEt, passwordEt, confirmPasswordEt, emailEt, phoneEt, addressEt, cityEt)
                inputs.forEach {
                    it.text = null
                }
            }
        }

        viewModel.loadingState.observe(this) {
            handle(it)
        }

        viewModel.saveUserState.observe(this) {
            handle(it)
        }

        viewModel.validationState.observe(this) {
            handle(it)
        }


    }
}