package id.syafii.pcstest.utils.network

sealed interface PcsResponse<out T> {
  object Loading : PcsResponse<Nothing>

  open class Error(
    open val message: String,
    val meta: Map<String, Any?> = mapOf(),
  ) : PcsResponse<Nothing>

  object Empty : PcsResponse<Nothing>

  class Success<T>(
    val data: T,
    val meta: Map<String, Any?> = mapOf(),
  ) : PcsResponse<T>
}