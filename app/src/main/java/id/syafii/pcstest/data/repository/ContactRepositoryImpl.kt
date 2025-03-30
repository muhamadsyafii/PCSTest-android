package id.syafii.pcstest.data.repository

import id.syafii.pcstest.data.remote.api.PcsApi
import id.syafii.pcstest.data.response.ContactResponse
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
  private val api : PcsApi
) : ContactRepository {

  override suspend fun fetchContact(): List<ContactResponse> {
    return api.fetchContact()
  }
}