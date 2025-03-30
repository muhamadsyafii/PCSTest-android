package id.syafii.pcstest.presentation.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.domain.usecase.ContactUseCase
import id.syafii.pcstest.utils.ext.toLiveData
import id.syafii.pcstest.utils.loading.LoadingState
import id.syafii.pcstest.utils.network.PcsResponse
import id.syafii.pcstest.utils.network.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
  private val useCase : ContactUseCase
) : ViewModel() {
  private val _stateLoading = MutableStateFlow(LoadingState.NOTHING)
  private val _stateError = MutableLiveData<PcsResponse.Error>()
  private val _contactData = MutableLiveData<List<Contact>>()

  val contactData= _contactData.toLiveData()
  val stateLoading = _stateLoading.asStateFlow()
  val stateError = _stateError.toLiveData()

  fun fetchContact(){
    viewModelScope.launch {
      useCase.fetchContact()
        .onStart { _stateLoading.value = LoadingState.PROGRESS_LOADING }
        .onCompletion { _stateLoading.value = LoadingState.PROGRESS_STOP }
        .catch { exception ->
          val errorResponse = (exception as Exception).toError()
          _stateError.postValue(errorResponse)
        }
        .collectLatest{ result ->
          if (result is PcsResponse.Success){
            _contactData.postValue(result.data)
          }else if (result is PcsResponse.Error){
            _stateError.postValue(result)
          }
        }
    }
  }



}