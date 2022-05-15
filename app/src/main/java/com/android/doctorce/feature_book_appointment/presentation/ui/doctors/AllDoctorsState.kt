package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel

data class AllDoctorsState (
    val doctors: List<DoctorModel> = emptyList(),
    val isLoading: Boolean = false
)
