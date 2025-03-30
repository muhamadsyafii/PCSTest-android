package id.syafii.pcstest.utils.ext

import java.math.BigInteger

inline fun <T, reified R> ArrayList<T?>?.orNullArrayList(transform: (T) -> R, targetClass: R): ArrayList<R> {
  return if (this == null) arrayListOf()
  else {
    val arrayListOf = arrayListOf<R>()
    this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
    return arrayListOf
  }
}

inline fun <T, reified R> ArrayList<T>?.orNullArrayListNot(transform: (T) -> R, targetClass: R): ArrayList<R> {
  return if (this == null) arrayListOf()
  else {
    val arrayListOf = arrayListOf<R>()
    this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
    return arrayListOf
  }
}

inline fun <T, reified R> ArrayList<T>.orNullArrayListClean(transform: (T) -> R, targetClass: R): ArrayList<R> {
  val arrayListOf = arrayListOf<R>()
  this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
  return arrayListOf
}

inline fun <T, reified R> List<T?>?.orNullList(transform: (T) -> R, targetClass: R): List<R> {
  return if (this == null) listOf()
  else {
    val arrayListOf = arrayListOf<R>()
    this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
    return arrayListOf
  }
}

inline fun <T, reified R> List<T>?.orNullListNot(transform: (T) -> R): List<R> {
  return if (this == null) listOf()
  else {
    val arrayListOf = arrayListOf<R>()
    this.forEach { arrayListOf.add(transform(it)) }
    return arrayListOf
  }
}

inline fun <T, reified R> T?.orNullObject(transform: (T) -> R, targetClass: R): R {
  return if (this == null) targetClass
  else transform(this)
}

fun Any?.orNullString(defaultNull: String = ""): String {
  return when (this) {
    is String? -> if (!this.isNullOrEmpty()) this.orNull() else defaultNull
    is Int? -> this.orNull().toString()
    is Long? -> this.orNull().toString()
    else -> defaultNull
  }
}

fun Any?.orNullInt(defaultNull: Int = 0): Int {
  return when (this) {
    is String? -> when {
      this.orNull("0").isEmpty() -> defaultNull
      this.orNull("0").all { it.isDigit() } -> this.orNull("0").toInt()
      this.orNull("0").any { it.isDigit() } -> this.orNull("0").filter { it.isDigit() }.toInt()
      else -> defaultNull
    }
    is Int? -> this.orNull()
    else -> defaultNull
  }
}

fun Any?.orNullLong(defaultNull: Long = 0L): Long {
  return when (this) {
    is String? -> when {
      this.orNull("0").isEmpty() -> defaultNull
      this.orNull("0").all { it.isDigit() } -> this.orNull("0").toLong()
      this.orNull("0").any { it.isDigit() } -> this.orNull("0").filter { it.isDigit() }.toLong()
      else -> defaultNull
    }
    is Int? -> this.orNull().toLong()
    is Long? -> this.orNull()
    else -> defaultNull
  }
}

fun Boolean?.orNull(defaultNull: Boolean = false): Boolean = this ?: defaultNull

fun Float?.orNull(defaultNull: Float = 0f): Float = this ?: defaultNull

fun Double?.orNull(defaultNull: Double = 0.0): Double = this ?: defaultNull

fun Long?.orNull(defaultNull: Long = 0): Long = this ?: defaultNull

fun BigInteger?.orNull(defaultNull: BigInteger = 0.toBigInteger()): BigInteger = this ?: defaultNull

fun Int?.orNull(defaultNull: Int = 0): Int = this ?: defaultNull

fun <T> List<T>?.orNull(defaultNull: List<T> = emptyList()) = this ?: defaultNull

fun <K, V> Map<K, V>?.orNull(defaultNull: Map<K, V> = emptyMap()) = this ?: defaultNull

fun Int?.orNull(defaultNull: String = "0", functionIfNotNull: (s: String) -> String? = { null }) = if (this != null) {
  if (!functionIfNotNull(this.toString()).isNullOrEmpty()) functionIfNotNull(this.toString())
    ?: defaultNull
  else this.toString()
} else defaultNull

fun String?.orNull(defaultNull: String = "", functionIfNotNull: (s: String) -> String? = { null }): String = if (this != null) {
  if (!functionIfNotNull(this).isNullOrEmpty()) functionIfNotNull(this)
    ?: defaultNull
  else this
} else defaultNull
