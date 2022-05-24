package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import javax.inject.Inject

data class AddPatientDetailsValidationUseCases @Inject constructor(
    val validateFullNameUseCase: ValidateFullNameUseCase,
    val validateDateOfBirthUseCase: ValidateDateOfBirthUseCase,
    val validateAddressUseCase: ValidateAddressUseCase,
    val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    val validateGenderUseCase: ValidateGenderUseCase
)
