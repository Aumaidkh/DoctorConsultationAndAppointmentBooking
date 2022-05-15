package com.android.doctorce.feature_book_appointment.domain.use_case

import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.repository.DoctorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDoctorsByCategoryUseCase @Inject constructor(
    private val repository: DoctorsRepository
) {
    suspend operator fun invoke(category: String): Flow<Resource<List<DoctorModel>>> {
        return repository.fetchAllDoctorsByCategory(category)
    }
}