package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

sealed class AddPatientDetailsEvent{
    data class FullNameChanged(val name: String): AddPatientDetailsEvent()
    data class DateOfBirthChanged(val dob: String): AddPatientDetailsEvent()
    data class AddressChanged(val address: String): AddPatientDetailsEvent()
    data class PhoneNumberChanged(val phone: String): AddPatientDetailsEvent()
    data class SelectedGender(val isGenderSelected: Boolean): AddPatientDetailsEvent()

    object Submit: AddPatientDetailsEvent()
}
