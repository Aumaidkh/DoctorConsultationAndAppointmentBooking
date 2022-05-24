package com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases

import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.domain.use_case.ValidationResult
import com.android.doctorce.feature_book_appointment.domain.util.Constants.ADDRESS_VALIDATION_REGEX
import com.android.doctorce.feature_book_appointment.domain.util.UiText

class ValidateAddressUseCase {
    operator fun invoke(address: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.address_cant_be_blank)
            )
        }
        if (!Regex(ADDRESS_VALIDATION_REGEX).matches(address)){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_address)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}