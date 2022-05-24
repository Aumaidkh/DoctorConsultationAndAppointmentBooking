package com.android.doctorce.feature_book_appointment.domain.use_case

import com.android.doctorce.feature_book_appointment.domain.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
