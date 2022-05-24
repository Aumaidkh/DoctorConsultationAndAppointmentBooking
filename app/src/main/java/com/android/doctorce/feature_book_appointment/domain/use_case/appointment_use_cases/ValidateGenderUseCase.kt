package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.ADDRESS_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.UiText

class ValidateGenderUseCase {
    operator fun invoke(isGenderSelected: Boolean): ValidationResult {
        if (!isGenderSelected) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.no_gender_selected)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}