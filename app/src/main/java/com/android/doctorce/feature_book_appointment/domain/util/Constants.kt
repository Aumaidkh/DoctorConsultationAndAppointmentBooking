package com.android.doctorce.feature_book_appointment.domain.util

object Constants {
    const val FULL_NAME_VALIDATION_REGEX = "^[\\p{L} .'-]+$"
    const val DATE_OF_BIRTH_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$"
    const val ADDRESS_VALIDATION_REGEX = "^[\\p{L}0-9 ,.'-]+$"
    const val PHONE_NUMBER_VALIDATION_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}\$"
}