package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.util.DoctorOrder
import com.android.doctorce.feature_book_appointment.domain.util.OrderType

data class AllDoctorsState (
    val doctors: List<DoctorModel> = emptyList(),
    val doctorsOrder: DoctorOrder = DoctorOrder.Fee(OrderType.Ascending),
    val isFilterSectionVisible: Boolean = false,
    val isLoading: Boolean = false
)
