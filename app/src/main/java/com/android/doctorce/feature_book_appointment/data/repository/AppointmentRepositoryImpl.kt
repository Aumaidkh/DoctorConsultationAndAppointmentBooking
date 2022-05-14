package com.android.doctorce.feature_book_appointment.data.repository

import com.android.doctorce.feature_book_appointment.data.remote.api.AppointmentsApi
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.AppointmentModel
import com.android.doctorce.feature_book_appointment.domain.repository.AppointmentRepository
import com.android.doctorce.feature_book_appointment.domain.util.toAppointmentModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentsApi
): AppointmentRepository {

    override suspend fun fetchAllAppointments(): Flow<Resource<List<AppointmentModel>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.fetchAllAppointments()
                if (response.isSuccessful && response.body()!=null){
                    val appointments = response.body()!!.map { it.toAppointmentModel() }
                    emit(Resource.Success(appointments))
                } else {
                    emit(Resource.Error("Something went wrong"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
                e.printStackTrace()
            }
            emit(Resource.Loading(false))
        }
    }
}