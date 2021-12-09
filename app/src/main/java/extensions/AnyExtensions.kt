package extensions
import android.util.Log

private const val LOG_TAG = "MyLogging"

fun Any.logD(message: String) {
    Log.d(LOG_TAG, message)
}

fun Any.logE(message: String) {
    Log.e(LOG_TAG, message)
}
