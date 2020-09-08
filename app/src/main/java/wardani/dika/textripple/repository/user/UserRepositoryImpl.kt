package wardani.dika.textripple.repository.user

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import wardani.dika.textripple.exception.NoDataException
import wardani.dika.textripple.exception.NotFoundException
import wardani.dika.textripple.model.User
import wardani.dika.textripple.util.Result
import wardani.dika.textripple.util.fromJson
import wardani.dika.textripple.util.toJson

class UserRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        val json = sharedPreferences.getString(USER_KEY, null)
        return if (json != null) {
            try {
                val type = object : TypeToken<List<User>>(){}.type
                val users = type.fromJson<List<User>>(json)
                Result.Success(users)
            } catch (e: Exception) {
                Result.Failed(e)
            }
        } else {
            Result.Failed(NoDataException("No data user available"))
        }
    }

    override suspend fun isUsernameExist(username: String): Result<Boolean> {
        return try {
            val json = sharedPreferences.getString(USER_KEY, null)
            val users = ArrayList<User>()

            if (json != null) {
                val type = object : TypeToken<List<User>>() {}.type
                val savedUsers = type.fromJson<List<User>>(json)
                users.addAll(savedUsers)
            }

            var isUsernameExist = false
            for (savedUser in users) {
                if (savedUser.email == username){
                    isUsernameExist = true
                    break
                }
            }

            Result.Success(isUsernameExist)
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }

    override suspend fun save(user: User): Result<Unit> {
        return try {
            val json = sharedPreferences.getString(USER_KEY, null)
            val users = ArrayList<User>()

            if (json != null) {
                val type = object : TypeToken<List<User>>() {}.type
                val savedUsers = type.fromJson<List<User>>(json)
                users.addAll(savedUsers)
            }

            users.add(user)
            val newJson = users.toJson()
            sharedPreferences.edit()
                .putString(USER_KEY, newJson)
                .apply()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }

    override suspend fun getDetail(username: String): Result<User> {
        return try {
            val json = sharedPreferences.getString(USER_KEY, null)
            if (json != null) {
                val type = object : TypeToken<List<User>>(){}.type
                val users = type.fromJson<List<User>>(json)
                var foundUser: User? = null

                for (user in users) {
                    if (user.email == username) {
                        foundUser = user
                        break
                    }
                }

                if (foundUser != null) {
                    Result.Success(foundUser)
                } else {
                    Result.Failed(NotFoundException("No data user has been found for username : $username"))
                }
            } else {
                Result.Failed(NoDataException("No data user available"))
            }
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }

    companion object {
        private const val USER_KEY = "USER"
    }
}