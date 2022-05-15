package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

sealed class AllDoctorsEvent{
    object FilterClick: AllDoctorsEvent()
    class Search(query: String): AllDoctorsEvent()
}
