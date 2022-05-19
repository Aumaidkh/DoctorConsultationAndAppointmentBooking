package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.R
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.use_case.doctors_use_case.DoctorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDetailsViewModel @Inject constructor(
    private val doctorsUseCase: DoctorsUseCase,
    val app: Application
): AndroidViewModel(app) {

    private val _errorChannel = Channel<String>()
    val errorChannel get() = _errorChannel.receiveAsFlow()

    init {
        fetchDoctorDetails()
    }

    fun onEvent(event: DoctorDetailsEvent){
        when(event){
            is DoctorDetailsEvent.AddToFavoritesEvent -> {
                // Add Doctor to favorites logic
            }
            is DoctorDetailsEvent.BookAppointmentEvent -> {
                // Book Appointment Logic here
            }
            is DoctorDetailsEvent.ShareEvent -> {
                // Share Event Here
            }
        }
    }

    private val _doctorDetailState : MutableStateFlow<DoctorDetailsState> = MutableStateFlow(
        DoctorDetailsState()
    )
    val doctorDetailState get() = _doctorDetailState.asStateFlow()

    private fun fetchDoctorDetails(){
        viewModelScope.launch {
            doctorsUseCase.fetchDoctorDetailsByIdUseCase.invoke().collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _doctorDetailState.value = _doctorDetailState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.let { doctorModel ->
                            _doctorDetailState.value = _doctorDetailState.value.copy(isLoading = false, doctorModel = doctorModel)
                        }
                    }

                    is Resource.Error -> {
                        _errorChannel.send(app.resources.getString(R.string.error_finding_doctor_details))
                    }
                }
            }
        }
    }

}