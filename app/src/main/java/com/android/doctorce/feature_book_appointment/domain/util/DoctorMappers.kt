package com.android.doctorce.feature_book_appointment.domain.util

import com.android.doctorce.feature_book_appointment.data.remote.dto.DoctorDto
import com.android.doctorce.feature_book_appointment.data.remote.dto.DoctorPortfolioDto
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.model.DoctorPortfolioModel


fun DoctorDto.toDoctorModel() =
    DoctorModel(
        name = name,
        speciality = speciality,
        posting = posting,
        imageUrl = imageUrl,
        aboutDoctor = aboutDoctor,
        portfolioModel = portfolioDto.toDoctorPortfolioModel(),
        consultationFee = consultationFee
    )


fun DoctorModel.toDoctorDto() =
    DoctorDto(
        name = name,
        speciality = speciality,
        posting = posting,
        imageUrl = imageUrl,
        aboutDoctor = aboutDoctor,
        portfolioDto = portfolioModel.toDoctorPortfolioDto(),
        consultationFee = consultationFee
    )

fun DoctorPortfolioModel.toDoctorPortfolioDto() =
    DoctorPortfolioDto(
        numberOfPatients = numberOfPatients,
        experienceInYears = experienceInYears,
        totalReviews = totalReviews,
        totalRatings = totalRatings
    )

fun DoctorPortfolioDto.toDoctorPortfolioModel() =
    DoctorPortfolioModel(
        numberOfPatients = numberOfPatients,
        experienceInYears = experienceInYears,
        totalReviews = totalReviews,
        totalRatings = totalRatings
    )