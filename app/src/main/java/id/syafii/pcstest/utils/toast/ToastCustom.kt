package id.syafii.pcstest.utils.toast
/*
 * Created by Muhamad Syafii
 * 30/3/2025 - muhamadsyafii4@gmail.com
 * Copyright (c) 2025.
 * All Rights Reserved
 */

import android.content.Context
import android.text.SpannedString
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import id.syafii.pcstest.R
import id.syafii.pcstest.utils.ext.findActivity
import id.syafii.pcstest.utils.ext.gone
import id.syafii.pcstest.utils.ext.visible
import id.syafii.pcstest.utils.toast.ToastType.TOAST_ERROR_CONNECTION
import id.syafii.pcstest.utils.toast.ToastType.TOAST_ERROR_DEFAULT
import id.syafii.pcstest.utils.toast.ToastType.TOAST_SUCCESS_DEFAULT

object ToastCustom {
  fun showCustomToast(context: Context, typeToast: String, message: String? = null, messageSpanned: SpannedString? = null, showIcon : Boolean? = null, isTop : Boolean = false) {
    if (!message.isNullOrEmpty() || !messageSpanned.isNullOrEmpty()) {
      val toast = Toast(context)
      toast.apply {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val activity = context.findActivity()
        val layout = if (activity != null) {
          inflater.inflate(R.layout.custom_toast, activity.findViewById(R.id.ConstraintToast))
        } else {
          inflater.inflate(R.layout.custom_toast, null) // fallback, misal buat testing
        }

        var mcvCardView = layout.findViewById<MaterialCardView>(R.id.mcvCard)
        var ivIcon = layout.findViewById<ImageView>(R.id.ivIcon)
        var tvMessage = layout.findViewById<TextView>(R.id.tvMessage)

        when (typeToast) {
          TOAST_ERROR_CONNECTION.string -> {
            mcvCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_low))
            if (showIcon == true) {
              ivIcon.visible()
              ivIcon.setImageResource(R.drawable.ic_wifi_off)
            }else{
              ivIcon.gone()
            }
            tvMessage.setTextColor(ContextCompat.getColor(context, R.color.red))
          }
          TOAST_ERROR_DEFAULT.string -> {
            mcvCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_low))
            if (showIcon == true) {
              ivIcon.visible()
              ivIcon.setImageResource(R.drawable.ic_error_outline)
            }else{
              ivIcon.gone()
            }
            tvMessage.setTextColor(ContextCompat.getColor(context, R.color.red))
          }
          TOAST_SUCCESS_DEFAULT.string -> {
            mcvCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_granny_apple))
            if (showIcon == true) {
              ivIcon.visible()
              ivIcon.setImageResource(R.drawable.ic_success)
            }else{
              ivIcon.gone()
            }
            tvMessage.setTextColor(ContextCompat.getColor(context, R.color.alert_info_text))
          }
        }

        tvMessage.text = message ?: messageSpanned

        duration = Toast.LENGTH_SHORT
        view = layout
//                setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        if (isTop) {
          setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        } else {
          setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        }
        show()
      }
    }
  }

  fun showToastSpanned(context: Context, typeToast: String, message: SpannedString) {
    showCustomToast(context, typeToast, messageSpanned = message)
  }
}