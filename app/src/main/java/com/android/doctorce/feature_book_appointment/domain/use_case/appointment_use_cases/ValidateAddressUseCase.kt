package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.ADDRESS_VALIDATION_REGEX

class ValidateAddressUseCase {
    operator fun invoke(address: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Address can't be blank"
            )
        }
        if (!Regex(ADDRESS_VALIDATION_REGEX).matches(address)){
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Address"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}