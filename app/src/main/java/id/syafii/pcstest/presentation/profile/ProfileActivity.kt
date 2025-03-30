package id.syafii.pcstest.presentation.profile

import dagger.hilt.android.AndroidEntryPoint
import id.syafii.pcstest.R
import id.syafii.pcstest.base.BaseActivity
import id.syafii.pcstest.databinding.ActivityProfileBinding
import id.syafii.pcstest.utils.ext.setupToolbar

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

  override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)

  override fun initView() {
    with(binding){
      setupToolbar(
        toolbarBinding = icToolbar,
        title = getString(R.string.label_profile),
        showBackButton = true,
        showProfileIcon = false
      )
    }
  }
}