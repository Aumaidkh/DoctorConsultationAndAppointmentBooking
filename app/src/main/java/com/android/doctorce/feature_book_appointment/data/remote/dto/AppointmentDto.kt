package com.android.doctorce.feature_book_appointment.data.remote.dto

data class AppointmentDto(
    val appointment_date: Int,
    val doctor_name: String,
    val image_url: String,
    val phone_number: Long,
    val speciality: String,
    val status: String
)