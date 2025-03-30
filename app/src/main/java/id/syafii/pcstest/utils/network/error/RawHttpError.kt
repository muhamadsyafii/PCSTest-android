package id.syafii.pcstest.utils.network.error

import com.google.gson.annotations.SerializedName

internal data class RawHttpError(
  @SerializedName("message")
  val message: String?,
  @SerializedName("code")
  val code: Int?,
  @SerializedName("data")
  val data: Any?,
)