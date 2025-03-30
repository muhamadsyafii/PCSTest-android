package id.syafii.pcstest.utils.network

import com.google.gson.Gson
import id.syafii.pcstest.utils.network.error.RawHttpError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/** Error type when no internet connection. */
object NoInternetError : PcsResponse.Error(message = "No Internet")

/** Error type when connection is time out. */
object TimeOutError : PcsResponse.Error(message = "Time Out")

/** Error type when API response is not 2xx code. */
data class HttpError(
  override val message: String,
  val code: Int,
  val data: Any?,
) : PcsResponse.Error(message = message)


/** [data class/object] WhateverError : Kassir.Error(message = ..., meta = ...) */
fun Exception.toError(): PcsResponse.Error {
  return try {
    when {
      this is IOException && message == "No Internet" -> {
        NoInternetError
      }
      this is HttpException -> {
        val error = Gson().fromJson(
          response()?.errorBody()?.string().orEmpty(),
          RawHttpError::class.java,
        )

        HttpError(
          message = error.message ?: message(),
          code = error.code ?: code(),
          data = error.data,
        )
      }
      this is SocketTimeoutException -> {
        TimeOutError
      }
      this is UnknownHostException -> {
        NoInternetError
      }
      /**
       * Feel free to add other general error type here
       * ...
       */
      else -> {
        PcsResponse.Error(message = message.orEmpty())
      }
    }
  } catch (e: Exception) {
    PcsResponse.Error(message = e.message.orEmpty())
  }
}