package com.android.doctorce.feature_book_appointment.domain.use_case.doctors_use_case

import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.repository.DoctorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDoctorDetailsByIdUseCase @Inject constructor(
    val repository: DoctorsRepository
) {

    suspend operator fun invoke(doctorId: Int = 0): Flow<Resource<DoctorModel>> {
        return repository.fetchDoctorDetailsById(doctorId)
    }
}