package com.android.doctorce.feature_book_appointment.presentation.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.feature_book_appointment.domain.model.BookAppointmentModel
import com.android.doctorce.feature_book_appointment.domain.model.PatientModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.AppointmentDateUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private var _bookingState = MutableStateFlow(BookAppointmentModel())
    val bookingState = _bookingState.asStateFlow()

    private var _channel = Channel<ProcessBooking>()
    val channel = _channel.receiveAsFlow()

    fun onEvent(event: SharedViewModelEvent){
        when(event) {
            is SharedViewModelEvent.SaveDoctorDetails -> {
                _bookingState.value = _bookingState.value.copy(
                    userId = event.userId,
                    doctorId = event.doctorId,
                    appointmentDate = event.appointmentDate
                )
            }
            is SharedViewModelEvent.SavePatientDetailsEvent -> {
                _bookingState.value = _bookingState.value.copy(
                    patient = event.patientModel
                )
            }

            is SharedViewModelEvent.SavePaymentDetailsEvent -> {
                _bookingState.value = _bookingState.value.copy(
                    consultationType = event.consultationFee,
                    timestamp = event.timestamp,
                    bookingId = event.bookingId,
                    bookingStatus = event.bookingStatus
                )
            }

            is SharedViewModelEvent.PayNowEvent -> {

            }

        }
        viewModelScope.launch {
            _channel.send(ProcessBooking.Success)
        }
    }

}

sealed class ProcessBooking {
    object Success: ProcessBooking()
}

sealed class SharedViewModelEvent{
    data class SaveDoctorDetails(
        val userId: Int,
        val doctorId: Int,
        val appointmentDate: AppointmentDateUiState
    ): SharedViewModelEvent()

    data class SavePatientDetailsEvent(
        val patientModel: PatientModel
    ): SharedViewModelEvent()

    data class SavePaymentDetailsEvent(
        val timestamp: Long,
        val consultationFee: Int,
        val bookingStatus: Int,
        val bookingId: Int
        ): SharedViewModelEvent()

    object PayNowEvent: SharedViewModelEvent()
}