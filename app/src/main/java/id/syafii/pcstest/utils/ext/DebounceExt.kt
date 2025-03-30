package id.syafii.pcstest.utils.ext

import android.os.SystemClock
import android.view.View

/**
 * Extension function to debounce clicks on a View.
 *
 * @param intervalMillis The interval in milliseconds within which consecutive clicks will be ignored.
 * @param onClick Action to be executed when the debounced click is confirmed.
 * Link : https://www.linkedin.com/posts/muhamadsyafii4_androiddevelopment-kotlin-debounce-activity-7274623877199118336-LQ2n?utm_source=social_share_send&utm_medium=member_desktop_web&rcm=ACoAACaQRCsB2Y1RnjHyQqHALsP66oxdLP7oYL8
 */
fun View.debounceClick(intervalMillis: Long = 500L, onClick: (View) -> Unit) {
  setOnClickListener { view ->
    val currentTime = SystemClock.elapsedRealtime()
    val lastClickTime = (view.getTag(view.id) as? Long) ?: 0L
    if (currentTime - lastClickTime >= intervalMillis) {
      view.setTag(view.id, currentTime)
      onClick(view)
    }
  }
}