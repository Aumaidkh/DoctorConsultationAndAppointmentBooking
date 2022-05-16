package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import com.android.doctorce.feature_book_appointment.domain.util.DoctorOrder

sealed class AllDoctorsEvent{
    data class Order(val doctorOrder: DoctorOrder): AllDoctorsEvent()
    class Search(query: String): AllDoctorsEvent()
    object ToggleOrderSectionVisibility: AllDoctorsEvent()
}
