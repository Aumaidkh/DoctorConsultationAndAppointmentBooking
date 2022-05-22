package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppointmentDateUiState(
    val day: String,
    val date: String,
    var isSelected: Boolean
): Parcelable