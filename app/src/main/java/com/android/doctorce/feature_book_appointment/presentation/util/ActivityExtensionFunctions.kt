package com.android.doctorce.feature_book_appointment.presentation.util

import android.app.Activity
import com.android.doctorce.feature_book_appointment.domain.util.UiText

fun Activity.asString(uiText: UiText): String {
    return when(uiText) {
        is UiText.DynamicString -> uiText.value
        is UiText.StringResource -> resources.getString(uiText.resId)
    }
}