package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.doctorce.feature_book_appointment.data.util.Resource
import com.android.doctorce.feature_book_appointment.domain.use_case.FetchDoctorsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDoctorsViewModel @Inject constructor(
    private val fetchAllDoctorsByCategoryUseCase: FetchDoctorsByCategoryUseCase
): ViewModel() {

    val state = MutableStateFlow(AllDoctorsState())

    init {
        fetchAllDoctors()
    }

    fun onEvent(event: AllDoctorsEvent){
        when(event){
            is AllDoctorsEvent.Search -> {

            }
            is AllDoctorsEvent.FilterClick -> {

            }
        }
    }

    private fun fetchAllDoctors(){
        viewModelScope.launch {
            fetchAllDoctorsByCategoryUseCase.invoke("").collect { result ->
                when(result){
                    is Resource.Loading -> {
                        state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        result.data?.let { doctors ->
                            state.value = state.value.copy(isLoading = false, doctors = doctors)
                        }
                    }
                    is Resource.Error -> Unit
                }
            }
        }
    }
}