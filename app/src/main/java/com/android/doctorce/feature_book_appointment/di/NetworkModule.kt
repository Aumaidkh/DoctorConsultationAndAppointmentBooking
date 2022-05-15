package com.android.doctorce.feature_book_appointment.di

import com.android.doctorce.BuildConfig
import com.android.doctorce.feature_book_appointment.data.remote.api.AppointmentsApi
import com.android.doctorce.feature_book_appointment.data.remote.api.DoctorsApi
import com.android.doctorce.feature_book_appointment.data.repository.AppointmentRepositoryImpl
import com.android.doctorce.feature_book_appointment.data.repository.DoctorRepositoryImpl
import com.android.doctorce.feature_book_appointment.domain.repository.AppointmentRepository
import com.android.doctorce.feature_book_appointment.domain.repository.DoctorsRepository
import com.android.doctorce.feature_book_appointment.domain.use_case.FetchDoctorsByCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAppointmentRepository(api: AppointmentsApi): AppointmentRepository {
        return AppointmentRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDoctorRepository(api: DoctorsApi): DoctorsRepository {
        return DoctorRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideFetchDoctorsByCategoryUseCase(repository: DoctorsRepository): FetchDoctorsByCategoryUseCase =
        FetchDoctorsByCategoryUseCase(repository)

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideAppointmentsApi(retrofit: Retrofit): AppointmentsApi {
        return retrofit.create(AppointmentsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDoctorsApi(retrofit: Retrofit): DoctorsApi {
        return retrofit.create(DoctorsApi::class.java)
    }

}