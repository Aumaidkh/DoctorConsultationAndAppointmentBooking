package com.android.doctorce.feature_book_appointment.data.remote.api

import com.android.doctorce.feature_book_appointment.data.remote.dto.AppointmentDto
import retrofit2.Response
import retrofit2.http.GET

interface AppointmentsApi {

    @GET("v1/770b0549-8b03-48d4-bc2b-7345f834e853")
    suspend fun fetchAllAppointments(): Response<List<AppointmentDto>>

}