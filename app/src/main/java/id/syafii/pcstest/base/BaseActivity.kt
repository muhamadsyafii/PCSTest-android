package id.syafii.pcstest.base

import android.os.Bundle
import android.text.SpannedString
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import id.syafii.pcstest.utils.loading.ProgressUtils
import id.syafii.pcstest.utils.network.NetworkUtil
import id.syafii.pcstest.utils.toast.ToastCustom
import id.syafii.pcstest.utils.toast.ToastType.TOAST_ERROR_CONNECTION

/*
 * Created by Muhamad Syafii
 * 30/3/2025 - muhamadsyafii4@gmail.com
 * Copyright (c) 2025.
 * All Rights Reserved
 */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(),
  NetworkUtil.NetworkChangeListener {

  protected lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = getViewBinding()
    setContentView(binding.root)
    initView()
    subscribeToLiveData()
  }

  abstract fun getViewBinding(): VB

  protected open fun subscribeToLiveData() {}

  protected open fun initView() {}

  override fun onNetworkChanged(isConnected: Boolean) {
    displayNetworkStatus(isConnected = isConnected)
  }

  override fun onStart() {
    super.onStart()
    NetworkUtil.registerNetworkCallback(this)
  }

  // Show loading indicator
  fun showProgressLoading() {
    ProgressUtils.show(binding.root.context)
  }

  // Hide loading indicator
  fun hideProgressLoading() {
    ProgressUtils.hide()
  }

  override fun onStop() {
    super.onStop()
    NetworkUtil.unregisterNetworkCallback(this)
  }

  fun showToast(typeToast: String, message: String) {
    ToastCustom.showCustomToast(this, typeToast = typeToast, message = message)
  }

  fun showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  fun showToast(typeToast: String, message: SpannedString) {
    ToastCustom.showToastSpanned(this, typeToast = typeToast, message = message)
  }

  protected open fun handleLoading(show: Boolean) {
    if (show) showProgressLoading() else hideProgressLoading()
  }

  private fun displayNetworkStatus(isConnected: Boolean) {
    if (!isConnected) {
      ToastCustom.showCustomToast(
        binding.root.context,
        TOAST_ERROR_CONNECTION.string,
        "No internet connection",
        showIcon = true,
        isTop = true
      )
    }
  }


}