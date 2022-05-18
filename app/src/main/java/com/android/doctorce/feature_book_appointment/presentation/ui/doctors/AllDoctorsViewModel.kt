package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.use_case.FetchDoctorsByCategoryUseCase
import com.android.doctorce.feature_book_appointment.domain.util.DoctorOrder
import com.android.doctorce.feature_book_appointment.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDoctorsViewModel @Inject constructor(
    private val fetchAllDoctorsByCategoryUseCase: FetchDoctorsByCategoryUseCase
): ViewModel() {

    val state = MutableStateFlow(AllDoctorsState())

    private val _infoChannel = Channel<String>()
    val infoChannel get() = _infoChannel.receiveAsFlow()


    init {
        fetchAllDoctors()
    }

    fun onEvent(event: AllDoctorsEvent){
        when(event){
            is AllDoctorsEvent.Search -> {

            }
            is AllDoctorsEvent.Order -> {
                if (state.value.doctorsOrder::class == event.doctorOrder::class &&
                        state.value.doctorsOrder.orderType == event.doctorOrder.orderType){
                    return
                }
                fetchAllDoctors("",event.doctorOrder)
            }

            is AllDoctorsEvent.ToggleOrderSectionVisibility -> {
                state.value = state.value.copy(isFilterSectionVisible = !state.value.isFilterSectionVisible)
            }

            is AllDoctorsEvent.Retry -> {
                fetchAllDoctors()
            }
        }
    }


    private fun fetchAllDoctors(category: String = "", doctorOrder: DoctorOrder = DoctorOrder.Fee(OrderType.Ascending)){
        viewModelScope.launch {
            fetchAllDoctorsByCategoryUseCase.invoke(category,doctorOrder).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        delay(500)
                        result.data?.let { doctors ->
                            state.value = state.value.copy(isLoading = false, doctors = doctors)
                        }
                    }
                    is Resource.Error -> {
                        _infoChannel.send(result.message.toString())
                    }
                }
            }
        }
    }
}