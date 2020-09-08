package wardani.dika.textripple.util

inline fun <reified T> tryCast(instance: Any?, block: T.() -> Unit) {
    if (instance is T) {
        block(instance)
    }
}