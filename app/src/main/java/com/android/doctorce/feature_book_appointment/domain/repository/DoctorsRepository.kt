package com.android.doctorce.feature_book_appointment.domain.repository

import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import kotlinx.coroutines.flow.Flow

interface DoctorsRepository {

    suspend fun fetchAllDoctorsByCategory(category: String): Flow<Resource<List<DoctorModel>>>
}