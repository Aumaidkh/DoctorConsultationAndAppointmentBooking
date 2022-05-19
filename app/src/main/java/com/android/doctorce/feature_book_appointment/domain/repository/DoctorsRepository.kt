package com.android.doctorce.feature_book_appointment.domain.repository

import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.util.DoctorOrder
import kotlinx.coroutines.flow.Flow

interface DoctorsRepository {

    suspend fun fetchAllDoctorsByCategory(category: String, doctorOrder: DoctorOrder): Flow<Resource<List<DoctorModel>>>

    suspend fun fetchDoctorDetailsById(doctorId: Int = 0): Flow<Resource<DoctorModel>>
}