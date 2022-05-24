package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.FULL_NAME_VALIDATION_REGEX

class ValidateFullNameUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Full Name can't be blank"
            )
        }
        if (!Regex(FULL_NAME_VALIDATION_REGEX).matches(name)){
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid name"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}