package com.android.doctorce.feature_book_appointment.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientModel(
    val fullName: String,
    val gender: String,
    val dateOfBirth: String,
    val address: String,
    val phoneNumber: String
): Parcelable
