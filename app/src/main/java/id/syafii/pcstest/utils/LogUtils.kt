package id.syafii.pcstest.utils

import android.util.Log
import id.syafii.pcstest.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LogUtils {
  private const val dateFormat = "yyyy-MM-dd HH:mm:ss.SSS"

  fun d(message: String) {
    if (BuildConfig.DEBUG) {
      Log.d(this::class.java.simpleName, message)
    }
  }

  fun d(tagName : String, message: String) {
    if (BuildConfig.DEBUG) {
      Log.d(tagName, message)
    }
  }

  fun i(message: String) {
    if (BuildConfig.DEBUG) {
      Log.i(this::class.java.simpleName, message)
    }
  }

  fun w(message: String) {
    if (BuildConfig.DEBUG) {
      Log.w(this::class.java.simpleName, message)
    }
  }

  fun e(message: String) {
    if (BuildConfig.DEBUG) {
      Log.e(this::class.java.simpleName, message)
    }
  }

  fun e(message: String, exception: Exception) {
    if (BuildConfig.DEBUG) {
      val formattedMessage = formatLogMessage(message, exception)
      Log.e(this::class.java.simpleName, formattedMessage)
    }
  }

  private fun formatLogMessage(message: String, exception: Exception): String {
    val timeStamp = SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())
    val stackTrace = Log.getStackTraceString(exception)
    val TAG = this::class.java.simpleName

    return "$timeStamp $TAG E/${getCallerInfo()} - $message\n$stackTrace"
  }

  private fun getCallerInfo(): String {
    val stackTrace = Thread.currentThread().stackTrace
    val callerElement = stackTrace[4] // Change index as needed to match your use case
    val className = callerElement.className
    val methodName = callerElement.methodName
    val lineNumber = callerElement.lineNumber
    return "$className.$methodName - $lineNumber"
  }
}