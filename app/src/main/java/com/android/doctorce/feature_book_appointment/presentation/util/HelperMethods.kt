package com.android.doctorce.feature_book_appointment.presentation.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.AppointmentDateUiState
import java.util.*

object HelperMethods {

    fun getCalculatedDate(dateFormat: String?, days: Int): List<AppointmentDateUiState> {
        val dates = mutableListOf<AppointmentDateUiState>()
        for (i in 1..days){
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            cal.add(Calendar.DAY_OF_YEAR, i)
            val day = s.format(Date(cal.timeInMillis)).split(",")[0]
            val date = s.format(Date(cal.timeInMillis)).split(",")[1]
            dates.add(AppointmentDateUiState(day,date,false))
        }
        return dates
    }
}