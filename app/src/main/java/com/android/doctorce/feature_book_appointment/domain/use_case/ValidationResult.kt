package com.android.doctorce.feature_book_appointment.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
