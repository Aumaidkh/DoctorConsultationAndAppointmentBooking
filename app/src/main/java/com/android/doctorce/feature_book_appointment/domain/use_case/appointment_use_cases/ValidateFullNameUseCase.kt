package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.FULL_NAME_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.UiText

class ValidateFullNameUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.full_name_cant_blank)
            )
        }
        if (!Regex(FULL_NAME_VALIDATION_REGEX).matches(name)){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_name)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}