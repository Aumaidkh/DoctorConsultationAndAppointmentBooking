package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

data class BookingFormState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val dateOfBirth: String = "",
    val dateOfBirthError: String? = null,
    val address: String = "",
    val addressError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val gender: String? = null,
    val genderSelected: Boolean = false,
    val genderError: String? = null
)
