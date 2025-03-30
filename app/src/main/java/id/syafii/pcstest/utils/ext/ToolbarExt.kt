package id.syafii.pcstest.utils.ext

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.syafii.pcstest.R
import id.syafii.pcstest.databinding.CustomToolbarBinding

fun CustomToolbarBinding.setupCustomToolbar(
  title: String,
  onBackClick: (() -> Unit)? = null,
  onProfileClick : (() -> Unit)? = null,
  showBackButton: Boolean = true,
  showProfileIcon: Boolean = true
) {
  tvTitleToolbar.text = title
  ivBack.visibility = if (showBackButton) View.VISIBLE else View.GONE
  ivProfile.visibility = if (showProfileIcon) View.VISIBLE else View.INVISIBLE

  ivBack.debounceClick {
    onBackClick?.invoke()
  }

  ivProfile.debounceClick {
    onProfileClick?.invoke()
  }
}


fun AppCompatActivity.setupToolbar(
  toolbarBinding: CustomToolbarBinding,
  title: String = getString(R.string.app_name),
  onBackClick: (() -> Unit)? = { onBackPressedDispatcher.onBackPressed() },
  onProfileClick: (() -> Unit)? = null,
  showBackButton: Boolean = true,
  showProfileIcon: Boolean = true
) {
  toolbarBinding.setupCustomToolbar(
    title = title,
    onBackClick = onBackClick,
    onProfileClick = onProfileClick,
    showBackButton = showBackButton,
    showProfileIcon = showProfileIcon
  )
}

