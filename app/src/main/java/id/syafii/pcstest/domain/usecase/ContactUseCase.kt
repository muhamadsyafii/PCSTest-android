package id.syafii.pcstest.domain.usecase

import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.utils.network.PcsResponse
import kotlinx.coroutines.flow.Flow

interface ContactUseCase {

  suspend fun fetchContact() : Flow<PcsResponse<List<Contact>>>
}