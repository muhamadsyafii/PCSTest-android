package id.syafii.pcstest.data.repository

import id.syafii.pcstest.data.response.ContactResponse

interface ContactRepository {

  suspend fun fetchContact() : List<ContactResponse>
}