package id.syafii.pcstest.utils.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.syafii.pcstest.utils.network.toError


fun AppCompatActivity.showToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
  Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> Fragment.openActivity(destination: Class<T>, bundleKey: String, id: String) {
  val intent = Intent(requireContext(), destination)
  intent.putExtra(bundleKey, id)
  startActivity(intent)
}

fun <T> Fragment.openActivity(destination: Class<T>) {
  val intent = Intent(requireContext(), destination)
  startActivity(intent)
}

fun <T> Activity.openActivity(destination: Class<T>, bundleKey: String, id: String) {
  val intent = Intent(this, destination)
  intent.putExtra(bundleKey, id)
  startActivity(intent)
}

fun <T> Activity.openActivity(destination: Class<T>) {
  val intent = Intent(this, destination)
  startActivity(intent)
}

fun Activity.closeActivity(){
  hideKeyboard()
  finish()
}

fun View.visible() {
  if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) this.visibility =
    View.VISIBLE
}

fun View.gone() {
  if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun View.invisible() {
  if (this.visibility == View.VISIBLE) this.visibility = View.INVISIBLE
}

fun View.visibility(visible: Boolean) {
  if (visible)
    this.visibility = View.VISIBLE
  else
    this.visibility = View.GONE
}

fun Activity.hideKeyboard() {
  val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  val view = currentFocus
  if (view != null) {
    inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
  }
}

fun Context.findActivity(): Activity? {
  var context = this
  while (context is ContextWrapper) {
    if (context is Activity) {
      return context
    }
    context = context.baseContext
  }
  return null
}

fun Throwable.toError() = (this as Exception).toError()

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

fun <T> List<T>.toArrayList(): ArrayList<T> {
  return runCatching { toMutableList() as ArrayList<T> }.getOrElse { arrayListOf() }
}