package wardani.dika.textripple.ui.activity.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wardani.dika.textripple.exception.ValidationException
import wardani.dika.textripple.model.User
import wardani.dika.textripple.repository.user.UserRepository
import wardani.dika.textripple.ui.LoadingState
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.toJson

enum class DataKey {
    NAME,
    PASSWORD,
    CONFIRM_PASSWORD,
    EMAIL,
    PHONE,
    ADDRESS,
    CITY
}

data class FieldResult(val key: DataKey, val message: String?)

data class ValidationResult(val valid: Boolean, val fieldResults: List<FieldResult>)


class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val loadingState: LiveData<LoadingState> = MutableLiveData()
    val saveUserState: LiveData<Result<Unit>> = MutableLiveData()
    val validationState: LiveData<ValidationResult> = MutableLiveData()

    private var currentValidationResult: ValidationResult? = null

    fun validate(user: User?, confirmPassword: String?) {
        if (user != null && confirmPassword != null) {
            val fieldResults = arrayListOf<FieldResult>()

            val nameValid = user.name.isNotBlank()
            fieldResults.add(FieldResult(DataKey.NAME, if (nameValid) null else "Name is required"))

            val emailValid = Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
            fieldResults.add(FieldResult(DataKey.EMAIL, if (emailValid) null else "Invalid email"))

            val passwordValid = user.password.isNotBlank() &&
                    user.password.length >= 6
            fieldResults.add(FieldResult(DataKey.PASSWORD, if (passwordValid) null else "Password must be 6 character or more"))

            val confirmPasswordValid = user.password == confirmPassword
            fieldResults.add(FieldResult(DataKey.CONFIRM_PASSWORD, if (confirmPasswordValid) null else "Confirm password didn't matched"))

            val phoneValid = Patterns.PHONE.matcher(user.phoneNumber).matches()
            fieldResults.add(FieldResult(DataKey.PHONE, if (phoneValid) null else "Phone number invalid"))

            val addressValid = user.address.isNotBlank()
            fieldResults.add(FieldResult(DataKey.ADDRESS, if (addressValid) null else "Address is required"))

            val cityValid = user.city.isNotBlank()
            fieldResults.add(FieldResult(DataKey.CITY, if (cityValid) null else "City us required"))

            val allFieldValid = nameValid && emailValid && passwordValid && confirmPasswordValid
                    && phoneValid && addressValid && cityValid

            val validationResult = ValidationResult(allFieldValid, fieldResults)
            currentValidationResult = validationResult
            (validationState as MutableLiveData).postValue(validationResult)
        } else {
            Log.d(TAG, "No data user and confirm password")
        }
    }

    fun save(user: User, confirmPassword: String) {

        Log.d(TAG, "TO SAVE : ${user.toJson()}")
        var validationResult = currentValidationResult

        if (validationResult == null) {
            validate(user, confirmPassword)
            validationResult = currentValidationResult
        }

        val saveState = saveUserState as MutableLiveData

        val valid = validationResult?.valid ?: false
        if (!valid) {
            saveState.postValue(Result.Failed(ValidationException("Some data are invalid, please check your input")))
            return
        }

        val loadingState = loadingState as MutableLiveData
        loadingState.postValue(LoadingState.LOADING)

        viewModelScope.launch {
            val result = userRepository.save(user)
            loadingState.postValue(LoadingState.FINISH)

            when (result) {
                is Result.Success -> {
                    saveState.postValue(Result.Success(Unit))
                }
                is Result.Failed ->{
                    saveState.postValue(Result.Failed(result.error))
                }
            }
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}