package id.syafii.pcstest.presentation.splash
/*
 * Created by Muhamad Syafii
 * 30/3/2025 - muhamadsyafii4@gmail.com
 * Copyright (c) 2025.
 * All Rights Reserved
 */

import androidx.lifecycle.lifecycleScope
import id.syafii.pcstest.base.BaseActivity
import id.syafii.pcstest.databinding.ActivityMainBinding
import id.syafii.pcstest.presentation.contact.ContactActivity
import id.syafii.pcstest.utils.ext.openActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

  override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

  override fun initView() {
    lifecycleScope.launch {
      delay(1500)
      openActivity(ContactActivity::class.java)
      finish()
    }
  }

}