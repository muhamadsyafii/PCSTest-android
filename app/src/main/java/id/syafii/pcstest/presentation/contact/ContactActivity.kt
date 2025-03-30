package id.syafii.pcstest.presentation.contact

import android.os.Build.VERSION_CODES
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.syafii.pcstest.R
import id.syafii.pcstest.base.BaseActivity
import id.syafii.pcstest.databinding.ActivityContactBinding
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.presentation.contact.adapter.ContactAdapter
import id.syafii.pcstest.presentation.contact.detail.DetailContactActivity
import id.syafii.pcstest.utils.ext.gone
import id.syafii.pcstest.utils.ext.handleOnBackPress
import id.syafii.pcstest.utils.ext.setupToolbar
import id.syafii.pcstest.utils.ext.visibility
import id.syafii.pcstest.utils.ext.visible
import id.syafii.pcstest.utils.loading.LoadingState
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactActivity : BaseActivity<ActivityContactBinding>() {
  override fun getViewBinding() = ActivityContactBinding.inflate(layoutInflater)
  private val contactAdapter by lazy { ContactAdapter().apply {
    onItemClick = { data ->
      handleItemClick(data)
    }
  } }

  private fun handleItemClick(data: Contact) {
    //TODO : still development
    startActivity(DetailContactActivity.newIntent(this@ContactActivity, data))
  }

  private val viewModel: ContactViewModel by viewModels()

  override fun onResume() {
    super.onResume()
    loadData()
  }

  override fun subscribeToLiveData() {
    lifecycleScope.launch {
      viewModel.stateLoading.collect { isLoading ->
        if (isLoading == LoadingState.PROGRESS_LOADING){
          showProgressLoading()
          showContent(isShown = false)
        } else {
          hideProgressLoading()
          showContent(isShown = true)
        }
      }
    }
    with(viewModel) {
      stateError.observe(this@ContactActivity) { error ->
        if (error != null) {
          hideProgressLoading()
          handleError(error)
        }
      }
      contactData.observe(this@ContactActivity, ::handleContactData)
    }
  }

  private fun handleContactData(data: List<Contact>) {
    with(binding) {
      if (data.isNotEmpty()) {
        contactAdapter.submitList(data)
        rvContact.visible()
        tvEmptyData.gone()
      } else {
        rvContact.gone()
        tvEmptyData.visible()
      }
    }
  }

  override fun initView() {
    with(binding) {
      setupAdapter()
      loadData()
      handleOnBackPress()
      setupToolbar(
        toolbarBinding = icToolbar,
        title = getString(R.string.app_name),
        showBackButton = false,
        showProfileIcon = true
      )

      swipeRefresh.setOnRefreshListener {
        swipeRefresh.isRefreshing = false
        loadData()
      }
    }
  }

  private fun setupAdapter() {
    binding.rvContact.apply {
      adapter = contactAdapter
      layoutManager = LinearLayoutManager(this@ContactActivity, RecyclerView.VERTICAL, false)
    }
  }

  fun showContent(isShown : Boolean){
    binding.swipeRefresh.visibility(isShown)
  }

  private fun loadData() {
    viewModel.fetchContact()
  }
}