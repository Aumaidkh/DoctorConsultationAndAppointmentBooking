package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.FULL_NAME_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.Constants.PHONE_NUMBER_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.UiText

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.phone_number_cant_be_blank)
            )
        }
        if (!Regex(PHONE_NUMBER_VALIDATION_REGEX).matches(phoneNumber)){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_phone_number)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}