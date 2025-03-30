package id.syafii.pcstest.utils.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuff.Mode.MULTIPLY
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import id.syafii.pcstest.R
import javax.sql.DataSource

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

fun ShapeableImageView.loadImageCenterCrop(imageUrl: String) {
  val progressDrawable = CircularProgressDrawable(this.context).apply {
    strokeWidth = 10f
    centerRadius = 20f
    setStyle(CircularProgressDrawable.LARGE)
    colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
    start()
  }

  if (imageUrl.isNotEmpty() && URLUtil.isValidUrl(imageUrl)) {
    this.setImageDrawable(progressDrawable)
    Glide.with(this)
      .load(imageUrl)
      .placeholder(progressDrawable)
      .error(R.drawable.camera_empty)
      .centerCrop()
      .listener(object : RequestListener<Drawable> {
        override fun onResourceReady(
          resource: Drawable,
          model: Any,
          target: Target<Drawable>?,
          dataSource: com.bumptech.glide.load.DataSource,
          isFirstResource: Boolean,
        ): Boolean {
          this@loadImageCenterCrop.setImageDrawable(resource)
          return false
        }

        override fun onLoadFailed(
          e: GlideException?,
          model: Any?,
          target: Target<Drawable>,
          isFirstResource: Boolean,
        ): Boolean {
          this@loadImageCenterCrop.setImageResource(R.drawable.camera_empty)
          return false
        }
      })
      .into(this)
  } else {
    this.setImageResource(R.drawable.camera_empty)
  }
}

@SuppressLint("CheckResult")
fun ImageView.loadImage(url: String?, placeholder: Int? = null, errorImage: Int? = null) {
  Glide.with(this.context)
    .load(url)
    .apply(RequestOptions().apply {
      placeholder?.let { placeholder(it) }
      errorImage?.let { error(it) }
      diskCacheStrategy(DiskCacheStrategy.ALL)
    })
    .into(this)
}
