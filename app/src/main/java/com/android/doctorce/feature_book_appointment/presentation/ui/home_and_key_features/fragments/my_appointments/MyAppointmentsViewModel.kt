package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments.my_appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppointmentsViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
): ViewModel() {

    var state = MutableStateFlow(MyAppointmentsState())

    init {
        getAllAppointments()
    }

    fun onEvent(event: MyAppointmentsEvent){
        when(event){
            is MyAppointmentsEvent.UpComingClick -> {
                getAllAppointments()
            }
            is MyAppointmentsEvent.PastClick -> {
                getAllAppointments()
            }
        }
    }

    private fun getAllAppointments(){
        viewModelScope.launch {
            appointmentRepository.fetchAllAppointments().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        result.data?.let { appointments ->
                            state.value = state.value.copy(appointments = appointments)
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state.value = state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}