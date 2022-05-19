package com.android.doctorce.feature_book_appointment.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val name: String,
    val speciality: String,
    val posting: String,
    val imageUrl: String,
    val aboutDoctor: String,
    val portfolioModel: DoctorPortfolioModel,
    val consultationFee: Double,
    val timings: String

): Parcelable

@Parcelize
data class DoctorPortfolioModel(
    val numberOfPatients: Int,
    val experienceInYears: Int,
    val totalReviews: Int,
    val totalRatings: Int,
): Parcelable