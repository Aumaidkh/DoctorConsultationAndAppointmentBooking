package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments.my_appointments

import com.android.doctorce.feature_book_appointment.domain.model.AppointmentModel

data class MyAppointmentsState (
    val appointments: List<AppointmentModel> = emptyList(),
    val isLoading: Boolean = false
)
