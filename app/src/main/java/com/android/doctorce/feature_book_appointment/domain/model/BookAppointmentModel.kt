package com.android.doctorce.feature_book_appointment.domain.model

import android.os.Parcelable
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.AppointmentDateUiState
import kotlinx.parcelize.Parcelize

/**
 * This Model will be used for booking appointments containing the following fields
 * 1. User Id: Who made the booking
 * 2. First Name of the patient
 * 3. Last Name of the patient
 * 4. Doctor ID
 * 5. Consultation type
 * 6. Booking Status
 * 7. TimeStamp
 * 8. Booking ID
 * */
@Parcelize
data class BookAppointmentModel(
    var userId: Int? = null,
    var firstName: String = "",
    var lastName: String = "",
    var doctorId: Int? = null,
    var consultationType: Int? = null,
    var bookingStatus: Int? = null,
    var timestamp: Long? = null,
    var bookingId: Int? = null,
    var appointmentDate: AppointmentDateUiState? = null
): Parcelable