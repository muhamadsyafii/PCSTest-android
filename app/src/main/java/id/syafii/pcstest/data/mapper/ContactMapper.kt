package id.syafii.pcstest.data.mapper

import id.syafii.pcstest.data.response.ContactResponse
import id.syafii.pcstest.domain.model.Contact
import id.syafii.pcstest.utils.ext.orNull

fun ContactResponse.toContact() = Contact(
  addressNo = addressNo.orNull(),
  avatar = avatar.orNull(),
  city = city.orNull(),
  country = country.orNull(),
  county = county.orNull(),
  createdAt = createdAt.orNull(),
  id = id.orNull(),
  name = name.orNull(),
  street = street.orNull(),
  zipCode = zipCode.orNull(),
)