package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import com.android.doctorce.feature_book_appointment.domain.model.BookAppointmentModel
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel

sealed class DoctorDetailsEvent{
    data class BookAppointmentEvent(val appointment: BookAppointmentModel): DoctorDetailsEvent()
    data class AddToFavoritesEvent(val doctor: DoctorModel): DoctorDetailsEvent()
    object ShareEvent: DoctorDetailsEvent()
}
