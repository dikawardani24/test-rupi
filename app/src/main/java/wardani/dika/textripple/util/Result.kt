package wardani.dika.textripple.util

sealed class Result<T> {
    data class Success<T>(val data: T): Result<T>()
    data class Failed<T>(val error: Throwable): Result<T>()
}