package com.android.doctorce.feature_book_appointment.domain.model

import com.google.gson.annotations.SerializedName

data class DoctorModel(
    val name: String,
    val speciality: String,
    val posting: String,
    val imageUrl: String,
    val aboutDoctor: String,
    val portfolioModel: DoctorPortfolioModel,
    val consultationFee: Double

)

data class DoctorPortfolioModel(
    val numberOfPatients: Int,
    val experienceInYears: Int,
    val totalReviews: Int,
    val totalRatings: Int,
)