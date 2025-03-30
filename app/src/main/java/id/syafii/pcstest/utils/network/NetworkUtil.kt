package id.syafii.pcstest.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import id.syafii.pcstest.utils.LogUtils

object NetworkUtil {

  interface NetworkChangeListener {
    fun onNetworkChanged(isConnected: Boolean)
  }

  private var networkChangeListener: NetworkChangeListener? = null

  fun setNetworkChangeListener(listener: NetworkChangeListener) {
    networkChangeListener = listener
  }

  private var networkCallback: NetworkCallback? = null
  private var isRegistered = false


  fun registerNetworkCallback(context: Context) {
    if (isRegistered) return // Mencegah register lebih dari sekali

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    networkCallback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        networkChangeListener?.onNetworkChanged(true) // Jaringan tersedia
      }

      override fun onLost(network: Network) {
        networkChangeListener?.onNetworkChanged(false) // Jaringan hilang
      }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      connectivityManager.registerDefaultNetworkCallback(networkCallback!!)
    } else {
      val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
      connectivityManager.registerNetworkCallback(request, networkCallback!!)
    }

    isRegistered = true
  }

  fun unregisterNetworkCallback(context: Context) {
    if (!isRegistered || networkCallback == null) return // Cegah unregister dua kali

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    try {
      connectivityManager.unregisterNetworkCallback(networkCallback!!)
    } catch (e: IllegalArgumentException) {
      LogUtils.w("NetworkCallback was already unregistered or not registered yet.")
    }
    networkCallback = null
    isRegistered = false
  }

  fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
      capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
  }


}