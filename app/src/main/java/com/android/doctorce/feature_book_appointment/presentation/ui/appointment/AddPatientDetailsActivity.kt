package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityAddPatientDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.BookAppointmentModel
import com.android.doctorce.feature_book_appointment.domain.model.PatientModel
import com.android.doctorce.feature_book_appointment.domain.util.UiText
import com.android.doctorce.feature_book_appointment.presentation.ui.common.ProcessBooking
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModelEvent
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.BookAppointmentActivity
import com.android.doctorce.feature_book_appointment.presentation.util.Constants.FEMALE
import com.android.doctorce.feature_book_appointment.presentation.util.Constants.MALE
import com.android.doctorce.feature_book_appointment.presentation.util.Constants.NEW_APPOINTMENT
import com.android.doctorce.feature_book_appointment.presentation.util.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddPatientDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityAddPatientDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddPatientDetailsViewModel by viewModels()
    private val bookingViewModel: SharedViewModel by viewModels()

    private var state: BookingFormState? = null


    private var pendingAppointment: BookAppointmentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_add_patient_details)

        pendingAppointment = intent?.getParcelableExtra(NEW_APPOINTMENT)

        setOnClickListeners()
        setupInputFields()

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
                viewModel.onEvent(AddPatientDetailsEvent.SelectedGender(true,MALE))
            }
            femaleCard.setOnClickListener {
                highlightSelectedGenderCard(it)
                viewModel.onEvent(AddPatientDetailsEvent.SelectedGender(true,FEMALE))
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
            binding.etFullName.error = asString(state.fullNameError)
        }
        if (state.genderError != null){
            binding.tvGender.text = asString(state.genderError)
            binding.tvGender.setTextColor(getColor(R.color.error))
        }
        if (state.dateOfBirthError != null){
            binding.etDateOfBirth.error = asString(state.dateOfBirthError)
        }
        if (state.addressError != null){
            binding.etAddress.error = asString(state.addressError)
        }
        if (state.phoneNumberError != null){
            binding.etPhone.error = asString(state.phoneNumberError)
        }
    }

    private fun setupObservers(){
        lifecycleScope.launchWhenStarted {
            viewModel.validationEvents.collect { event ->
                when(event){
                    is AddPatientDetailsViewModel.ValidationEvent.Success -> {
                        // Head to the next activity with the data
                        bookingViewModel.onEvent(SharedViewModelEvent.SavePatientDetailsEvent(
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
                        startActivity(Intent(this@AddPatientDetailsActivity,BookAppointmentActivity::class.java))
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