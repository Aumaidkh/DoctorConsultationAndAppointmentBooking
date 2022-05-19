package com.android.doctorce.feature_book_appointment.domain.use_case.doctors_use_case

import javax.inject.Inject

data class DoctorsUseCase @Inject constructor(
    val fetchDoctorsByCategoryUseCase: FetchDoctorsByCategoryUseCase,
    val fetchDoctorDetailsByIdUseCase: FetchDoctorDetailsByIdUseCase
)
