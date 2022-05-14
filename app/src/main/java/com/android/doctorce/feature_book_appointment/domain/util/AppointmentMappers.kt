package com.android.doctorce.feature_book_appointment.domain.util

import com.android.doctorce.feature_book_appointment.data.remote.dto.AppointmentDto
import com.android.doctorce.feature_book_appointment.domain.model.AppointmentModel

fun AppointmentDto.toAppointmentModel() =
    AppointmentModel(
        appointmentDate = appointment_date,
        doctorName = doctor_name,
        imageUrl = image_url,
        phoneNumber = phone_number,
        speciality = speciality,
        status = status
    )

fun AppointmentModel.toAppointmentDto() =
    AppointmentDto(
        appointment_date = appointmentDate,
        doctor_name = doctorName,
        image_url = imageUrl,
        phone_number = phoneNumber,
        speciality = speciality,
        status = status
    )