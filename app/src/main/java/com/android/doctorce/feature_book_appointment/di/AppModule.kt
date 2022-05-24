package com.android.doctorce.feature_book_appointment.di

import com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideValidateFullNameUseCase() =
        ValidateFullNameUseCase()

    @Singleton
    @Provides
    fun provideValidateGenderUseCase() =
        ValidateGenderUseCase()

    @Singleton
    @Provides
    fun provideValidateDateOfBirthUseCase() =
        ValidateDateOfBirthUseCase()

    @Singleton
    @Provides
    fun provideValidateAddressUseCase() =
        ValidateAddressUseCase()

    @Singleton
    @Provides
    fun provideValidatePhoneNumberUseCase() =
        ValidatePhoneNumberUseCase()

    @Singleton
    @Provides
    fun providePatientDetailsValidationUseCase(
        validateFullNameUseCase: ValidateFullNameUseCase,
        validateDateOfBirthUseCase: ValidateDateOfBirthUseCase,
        validateAddressUseCase: ValidateAddressUseCase,
        validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
        validateGenderUseCase: ValidateGenderUseCase
    ): AddPatientDetailsValidationUseCases {
        return AddPatientDetailsValidationUseCases(
             validateFullNameUseCase,
             validateDateOfBirthUseCase,
             validateAddressUseCase,
             validatePhoneNumberUseCase,
             validateGenderUseCase
        )
    }
}