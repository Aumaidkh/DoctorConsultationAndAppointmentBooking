package com.android.doctorce.feature_book_appointment.domain.model

data class AppointmentModel(
    val appointmentDate: Int,
    val doctorName: String,
    val imageUrl: String,
    val phoneNumber: Long,
    val speciality: String,
    val status: String
)