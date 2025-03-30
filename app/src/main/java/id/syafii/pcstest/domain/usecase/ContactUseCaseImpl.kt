package id.syafii.pcstest.domain.usecase

import id.syafii.pcstest.data.mapper.toContact
import id.syafii.pcstest.data.repository.ContactRepository
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.utils.ext.orNullListNot
import id.syafii.pcstest.utils.network.PcsResponse
import id.syafii.pcstest.utils.network.PcsResponse.Success
import id.syafii.pcstest.utils.network.toError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactUseCaseImpl @Inject constructor(
  private val repository: ContactRepository
) : ContactUseCase {

  override suspend fun fetchContact(): Flow<PcsResponse<List<Contact>>> =
    flow {
      val response = repository.fetchContact()
      if (response.isNotEmpty()) {
        emit(Success(response.orNullListNot { it.toContact() }))
      } else {
        emit(PcsResponse.Empty)
      }
    }.catch { throwable ->
      emit((throwable as Exception).toError())
    }.flowOn(Dispatchers.IO)
}