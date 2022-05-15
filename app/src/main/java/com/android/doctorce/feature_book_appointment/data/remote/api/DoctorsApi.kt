package com.android.doctorce.feature_book_appointment.data.remote.api

import com.android.doctorce.feature_book_appointment.data.remote.dto.DoctorDto
import retrofit2.Response
import retrofit2.http.GET

interface DoctorsApi {

    @GET("v1/4ca5332e-d02d-455a-8909-2e567c8f6c48")
    suspend fun fetchAllDoctorsByCategory(): Response<List<DoctorDto>>
}