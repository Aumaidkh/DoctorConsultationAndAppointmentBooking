package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.FULL_NAME_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.Constants.PHONE_NUMBER_VALIDATION_REGEX

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Phone Number can't be blank"
            )
        }
        if (!Regex(PHONE_NUMBER_VALIDATION_REGEX).matches(phoneNumber)){
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Phone Number"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}