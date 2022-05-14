package com.android.doctorce.feature_book_appointment.domain.repository

import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.AppointmentModel
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {

    suspend fun fetchAllAppointments(): Flow<Resource<List<AppointmentModel>>>

}