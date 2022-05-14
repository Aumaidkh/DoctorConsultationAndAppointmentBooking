package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments.my_appointments

sealed class MyAppointmentsEvent{
    object UpComingClick: MyAppointmentsEvent()
    object PastClick: MyAppointmentsEvent()
}
