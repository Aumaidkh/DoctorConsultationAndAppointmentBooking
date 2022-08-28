package com.android.doctorce.feature_book_appointment.presentation.ui.appointment.fragment.appointment_type_selection

import androidx.lifecycle.ViewModel
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModelEvent
import com.android.doctorce.feature_book_appointment.presentation.util.Constants.ONLINE_APPOINTMENT_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AppointmentTypeSelectionViewModel @Inject constructor(

): ViewModel() {

    /**
     * Holding State of the screen
     * */
    private var _state = MutableStateFlow(AppointmentTypeSelectionState())
    private var state = _state.asStateFlow()

    /**
     * Sending events using this channel
     * */
    private var _infoChannel = Channel<AppointmentTypeEvents>()
    val infoChannel = _infoChannel.receiveAsFlow()

    fun onEvent(event: SharedViewModelEvent){
        when(event){

            is SharedViewModelEvent.PayNowEvent -> {

            }
            else -> {}
        }
    }
}

/**
 * Appointment Type Screen State
 * */
data class AppointmentTypeSelectionState(
    var doctor: DoctorModel? = null,
    var appointmentType: Int = ONLINE_APPOINTMENT_TYPE,
    var totalCost: Double = 0.0,
    var totalDiscount: Double = 0.0,
    var totalPayment: Double = 0.0
)

/**
 * Class for handling events
 * */
sealed class AppointmentTypeEvents {
    object Success: AppointmentTypeEvents()
}