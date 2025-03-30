package id.syafii.pcstest.utils.ext

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff.Mode.MULTIPLY
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import id.syafii.pcstest.R

fun ImageView.loadImageCenterCrop(context: Context, imageUrl: String) {
  val progressDrawable = CircularProgressDrawable(context).apply {
    strokeWidth = 10f
    centerRadius = 20f
    setStyle(CircularProgressDrawable.LARGE)
    colorFilter = PorterDuffColorFilter(Color.WHITE, MULTIPLY)
    start()
  }

  if (imageUrl.isNotEmpty()) {
    this.setImageDrawable(progressDrawable)
    Glide.with(context)
      .load(imageUrl)
      .placeholder(progressDrawable)
      .error(R.drawable.camera_empty)
      .centerCrop()
      .into(object : CustomTarget<Drawable>() {
        override fun onLoadFailed(errorDrawable: Drawable?) {
          this@loadImageCenterCrop.setImageResource(R.drawable.camera_empty)
        }
        override fun onResourceReady(
          resource: Drawable,
          transition: Transition<in Drawable>?
        ) {
          this@loadImageCenterCrop.setImageDrawable(resource)
        }
        override fun onLoadCleared(placeholder: Drawable?) {}
      })
  } else {
    this.setImageResource(R.drawable.camera_empty)
  }
}