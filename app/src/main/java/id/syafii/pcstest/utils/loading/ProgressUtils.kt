package id.syafii.pcstest.utils.loading

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import id.syafii.pcstest.databinding.DialogProgressBinding

object ProgressUtils {

  private var dialog: Dialog? = null

  fun show(context: Context) {
    if (dialog?.isShowing == true) return
    if (context is Activity && (context.isFinishing || context.isDestroyed)) return
    val binding = DialogProgressBinding.inflate(LayoutInflater.from(context), null, false)
    dialog = Dialog(context).apply {
      setCancelable(false)
      requestWindowFeature(Window.FEATURE_NO_TITLE)
      setContentView(binding.root)
      window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialog?.show()
  }

  fun hide() {
    dialog?.let {
      if (it.isShowing && it.window?.decorView?.isAttachedToWindow == true) {
        it.dismiss()
        dialog = null
      }
    }
  }
}