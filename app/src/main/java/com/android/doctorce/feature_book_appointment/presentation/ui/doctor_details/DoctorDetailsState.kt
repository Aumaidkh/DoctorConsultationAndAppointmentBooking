package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel

data class DoctorDetailsState(
    val doctorModel: DoctorModel? = null,
    val appointmentDates: List<AppointmentDateUiState> = emptyList(),
    var isLoading: Boolean = false
)
