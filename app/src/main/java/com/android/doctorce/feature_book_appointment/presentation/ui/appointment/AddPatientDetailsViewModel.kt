package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.feature_book_appointment.domain.use_case.appointment_use_cases.AddPatientDetailsValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPatientDetailsViewModel @Inject constructor(
    private val validationUseCases: AddPatientDetailsValidationUseCases
): ViewModel() {

    var state = MutableStateFlow(BookingFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: AddPatientDetailsEvent){
        when(event){
            is AddPatientDetailsEvent.FullNameChanged -> {
                state.value = state.value.copy(fullName = event.name)
            }
            is AddPatientDetailsEvent.DateOfBirthChanged -> {
                state.value = state.value.copy(dateOfBirth = event.dob)
            }
            is AddPatientDetailsEvent.AddressChanged -> {
                state.value = state.value.copy(address = event.address)
            }
            is AddPatientDetailsEvent.PhoneNumberChanged -> {
                state.value = state.value.copy(phoneNumber = event.phone)
            }
            is AddPatientDetailsEvent.SelectedGender -> {
                state.value = state.value.copy(genderSelected = event.isGenderSelected, gender = event.gender)
            }
            is AddPatientDetailsEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val fullNameResult = validationUseCases.validateFullNameUseCase.invoke(state.value.fullName)
        val genderResult = validationUseCases.validateGenderUseCase.invoke(state.value.genderSelected)
        val dobResult = validationUseCases.validateDateOfBirthUseCase.invoke(state.value.dateOfBirth)
        val addressResult = validationUseCases.validateAddressUseCase.invoke(state.value.address)
        val phoneResult = validationUseCases.validatePhoneNumberUseCase.invoke(state.value.phoneNumber)

        val hasError = listOf(
            fullNameResult,
            genderResult,
            dobResult,
            addressResult,
            phoneResult
        ).any { !it.successful }


        if (hasError){
            state.value = state.value.copy(
                fullNameError = fullNameResult.errorMessage,
                genderError = genderResult.errorMessage,
                dateOfBirthError = dobResult.errorMessage,
                addressError = addressResult.errorMessage,
                phoneNumberError = phoneResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            // Notifying the Ui that validation is successful
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}