package wardani.dika.textripple.repository.user

import wardani.dika.textripple.model.User
import wardani.dika.textripple.util.Result

interface UserRepository {
    suspend fun save(user: User): Result<Unit>
    suspend fun getDetail(username: String): Result<User>
    suspend fun getUsers(): Result<List<User>>
    suspend fun isUsernameExist(username: String): Result<Boolean>
}