package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.DATE_OF_BIRTH_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.UiText

class ValidateDateOfBirthUseCase {

    operator fun invoke(dateOfBirth: String): ValidationResult {
        if (dateOfBirth.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.dob_cant_be_blank)
            )
        }
        if (!Regex(DATE_OF_BIRTH_VALIDATION_REGEX).matches(dateOfBirth)){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_date_of_birth)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}