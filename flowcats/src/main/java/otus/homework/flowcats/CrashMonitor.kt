package otus.homework.flowcats

import android.util.Log

object CrashMonitor {
    /**
     * Pretend this is Crashlytics/AppCenter
     */
    fun trackWarning(TAG: String, error:String?) {
        val message = error ?: "Неизвестная ошибка в CatsViewModel"
        Log.e(TAG,message)
    }
}