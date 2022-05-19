package com.android.doctorce.feature_book_appointment.data.remote.api

import com.android.doctorce.feature_book_appointment.data.remote.dto.DoctorDto
import retrofit2.Response
import retrofit2.http.GET

interface DoctorsApi {

    @GET("/v1/d0206011-dfdd-49d3-bb7b-b1689814aa1f")
    suspend fun fetchAllDoctorsByCategory(): Response<List<DoctorDto>>

    @GET("/v1/e0139b4a-bf3b-48f6-ac23-259c9cc765b6")
    suspend fun fetchDoctorDetailsById(): Response<DoctorDto>
}