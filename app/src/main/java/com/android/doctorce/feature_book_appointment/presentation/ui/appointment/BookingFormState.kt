package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import com.android.doctorce.feature_book_appointment.domain.util.UiText

data class BookingFormState(
    val fullName: String = "",
    val fullNameError: UiText? = null,
    val dateOfBirth: String = "",
    val dateOfBirthError: UiText? = null,
    val address: String = "",
    val addressError: UiText? = null,
    val phoneNumber: String = "",
    val phoneNumberError: UiText? = null,
    val gender: String? = null,
    val genderSelected: Boolean = false,
    val genderError: UiText? = null
)
