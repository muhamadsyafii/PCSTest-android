package id.syafii.pcstest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.syafii.pcstest.data.remote.api.PcsApi
import id.syafii.pcstest.data.repository.ContactRepository
import id.syafii.pcstest.data.repository.ContactRepositoryImpl
import id.syafii.pcstest.domain.usecase.ContactUseCase
import id.syafii.pcstest.domain.usecase.ContactUseCaseImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ContactModule {

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): PcsApi {
    return retrofit.create(PcsApi::class.java)
  }

  @Provides
  fun provideContactRepository(repositoryImpl: ContactRepositoryImpl): ContactRepository =
    repositoryImpl

  @Provides
  fun provideContactUseCase(repositoryImpl: ContactUseCaseImpl): ContactUseCase =
    repositoryImpl

}