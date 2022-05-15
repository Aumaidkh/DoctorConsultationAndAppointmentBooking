package com.android.doctorce.feature_book_appointment.data.repository

import com.android.doctorce.feature_book_appointment.data.remote.api.DoctorsApi
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.repository.DoctorsRepository
import com.android.doctorce.feature_book_appointment.domain.util.toDoctorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val api: DoctorsApi
): DoctorsRepository {

    override suspend fun fetchAllDoctorsByCategory(category: String): Flow<Resource<List<DoctorModel>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.fetchAllDoctorsByCategory()
                if (response.isSuccessful && response.body()!=null){
                    val doctors = response.body()!!.map { it.toDoctorModel() }
                    emit(Resource.Success(doctors))
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