package id.syafii.pcstest.presentation.contact.detail

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.syafii.pcstest.R
import id.syafii.pcstest.base.BaseActivity
import id.syafii.pcstest.data.mapper.getFormattedAddress
import id.syafii.pcstest.databinding.ActivityDetailContactBinding
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.utils.ext.loadImageCenterCrop
import id.syafii.pcstest.utils.ext.orNull
import id.syafii.pcstest.utils.ext.setupToolbar

@AndroidEntryPoint
class DetailContactActivity : BaseActivity<ActivityDetailContactBinding>() {

  override fun getViewBinding() = ActivityDetailContactBinding.inflate(layoutInflater)
  private val data by lazy { Gson().fromJson(intent?.getStringExtra(EXTRA_DATA), Contact::class.java) }

  override fun initView() {
    with(binding){
      setupToolbar(
        toolbarBinding = icToolbar,
        title = getString(R.string.app_name),
        showBackButton = true,
        showProfileIcon = false
      )
      ivContact.loadImageCenterCrop(context = this@DetailContactActivity, imageUrl = data.avatar)
      tvName.text = data.name.orNull("-")
      tvAddress.text = data.getFormattedAddress()
    }
  }
  companion object {
    const val EXTRA_DATA = "extra-data-contact"

    fun newIntent(
      context: Context,
      data : Contact
    ) : Intent {
      return Intent(context, DetailContactActivity::class.java).apply {
        putExtra(EXTRA_DATA, Gson().toJson(data))
      }
    }
  }
}