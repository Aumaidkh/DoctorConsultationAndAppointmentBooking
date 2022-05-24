package com.android.doctorce.feature_book_appointment.presentation.ui.appointment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.doctorce.R
import com.android.doctorce.databinding.FragmentPatientDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.PatientModel
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.AddPatientDetailsEvent
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.AddPatientDetailsViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.BookingFormState
import com.android.doctorce.feature_book_appointment.presentation.ui.common.ProcessBooking
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModelEvent
import com.android.doctorce.feature_book_appointment.presentation.util.Constants
import com.android.doctorce.feature_book_appointment.presentation.util.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PatientDetailsFragment : Fragment() {

    private var _binding: FragmentPatientDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddPatientDetailsViewModel by viewModels()
    private val bookingViewModel: SharedViewModel by activityViewModels()

    private var state: BookingFormState? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_patient_details,container,false)
        setOnClickListeners()
        setupInputFields()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setOnClickListeners() {

        binding.apply {
            btnSaveDetails.setOnClickListener {
                viewModel.onEvent(AddPatientDetailsEvent.Submit)
            }
        }
    }

    private fun setupInputFields(){
        binding.apply {
            maleCard.setOnClickListener {
                highlightSelectedGenderCard(it)
                viewModel.onEvent(AddPatientDetailsEvent.SelectedGender(true, Constants.MALE))
            }
            femaleCard.setOnClickListener {
                highlightSelectedGenderCard(it)
                viewModel.onEvent(AddPatientDetailsEvent.SelectedGender(true, Constants.FEMALE))
            }
            etFullName.doOnTextChanged { fullName, _, _, _ ->
                viewModel.onEvent(AddPatientDetailsEvent.FullNameChanged(fullName.toString()))
            }
            etDateOfBirth.doOnTextChanged { dob, _, _, _ ->
                viewModel.onEvent(AddPatientDetailsEvent.DateOfBirthChanged(dob.toString()))
            }
            etAddress.doOnTextChanged { address, _, _, _ ->
                viewModel.onEvent(AddPatientDetailsEvent.AddressChanged(address.toString()))
            }
            etPhone.doOnTextChanged { phoneNumber, _, _, _ ->
                viewModel.onEvent(AddPatientDetailsEvent.PhoneNumberChanged(phoneNumber.toString()))
            }
        }
    }

    private fun highlightSelectedGenderCard(selectedCard: View){
        // Reset Previous selections
        binding.apply {
            maleCard.setBackgroundResource(R.drawable.card_unselected_background)
            femaleCard.setBackgroundResource(R.drawable.card_unselected_background)
        }
        selectedCard.setBackgroundResource(R.drawable.card_selected_background)
    }

    private fun setupErrors(state: BookingFormState) {
        if (state.fullNameError != null){
            binding.etFullName.error = requireActivity().asString(state.fullNameError)
        }
        if (state.genderError != null){
            binding.tvGender.text = requireActivity().asString(state.genderError)
            binding.tvGender.setTextColor(resources.getColor(R.color.error))
        }
        if (state.dateOfBirthError != null){
            binding.etDateOfBirth.error = requireActivity().asString(state.dateOfBirthError)
        }
        if (state.addressError != null){
            binding.etAddress.error = requireActivity().asString(state.addressError)
        }
        if (state.phoneNumberError != null){
            binding.etPhone.error = requireActivity().asString(state.phoneNumberError)
        }
    }

    private fun setupObservers(){
        lifecycleScope.launchWhenStarted {
            viewModel.validationEvents.collect { event ->
                when(event){
                    is AddPatientDetailsViewModel.ValidationEvent.Success -> {
                        // Head to the next activity with the data
                        bookingViewModel.onEvent(
                            SharedViewModelEvent.SavePatientDetailsEvent(
                            PatientModel(
                                state!!.fullName,
                                state!!.gender!!,
                                state!!.dateOfBirth,
                                state!!.address,
                                state!!.phoneNumber
                            )
                        ))
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                state = it
                setupErrors(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            bookingViewModel.channel.collect { event ->
                when(event){
                    is ProcessBooking.Success -> {
                        findNavController().navigate(R.id.action_patientDetailsFragment_to_appointmentTypeSelectionFragment)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}