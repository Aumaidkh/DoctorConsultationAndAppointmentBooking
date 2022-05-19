package com.android.doctorce.feature_book_appointment.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DoctorDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("speciality")
    val speciality: String,
    @SerializedName("posting")
    val posting: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("intro")
    val aboutDoctor: String,
    @SerializedName("portfolio")
    val portfolioDto: DoctorPortfolioDto,
    @SerializedName("fee")
    val consultationFee: Double,
    @SerializedName("timings")
    val timings: String

)

data class DoctorPortfolioDto(
    @SerializedName("patients")
    val numberOfPatients: Int,
    @SerializedName("experience")
    val experienceInYears: Int,
    @SerializedName("reviews")
    val totalReviews: Int,
    @SerializedName("ratings")
    val totalRatings: Int,
)