package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.DATE_OF_BIRTH_VALIDATION_REGEX

class ValidateDateOfBirthUseCase {

    operator fun invoke(dateOfBirth: String): ValidationResult {
        if (dateOfBirth.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Date of birth can't be blank"
            )
        }
        if (!Regex(DATE_OF_BIRTH_VALIDATION_REGEX).matches(dateOfBirth)){
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid D.O.B"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}