package com.android.doctorce.feature_book_appointment.presentation.ui.appointment.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.doctorce.R
import com.android.doctorce.databinding.FragmentDoctorDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.AddPatientDetailsActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.common.ProcessBooking
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModelEvent
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.DoctorDetailsViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.adapters.AppointmentDateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "DoctorDetailsFragment"
@AndroidEntryPoint
class DoctorDetailsFragment : Fragment() {

    private var _binding: FragmentDoctorDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorDetailsViewModel by viewModels()
    private val bookingViewModel: SharedViewModel by activityViewModels()

    private lateinit var appointmentDateAdapter: AppointmentDateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_doctor_details,container,false)
        appointmentDateAdapter = AppointmentDateAdapter()
        setupClickListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupAppointmentDateRecyclerView()
        setupObservers()
    }

    private fun setupAppointmentDateRecyclerView(){
        binding.rvDateAndDay.apply {
            adapter = appointmentDateAdapter
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        }
    }


    /**
     * Observers
     * */
    private fun setupObservers(){

        /**
         * Observing Entire Doctor Details State
         * */

        lifecycleScope.launchWhenStarted {
            viewModel.doctorDetailState.collect { state ->
                if (!state.isLoading){
                    Log.d(TAG, "setupObservers: ${state.appointmentDates}")
                    appointmentDateAdapter.submitList(state.appointmentDates)
                    populateUi(state.doctorModel)

                } else {
                    // Show Loading indicator here
                }
            }
        }

        /**
         * Observing Errors here and showing them in a snack bar
         * */
        lifecycleScope.launchWhenStarted {
            viewModel.errorChannel.collect { error ->
                showCustomSnackBarWithMessage(error)
            }
        }

        lifecycleScope.launchWhenStarted {
            bookingViewModel.channel.collect { event ->
                when(event){
                    is ProcessBooking.Success -> {
                        findNavController().navigate(R.id.action_doctorDetailsFragment_to_patientDetailsFragment)
                    }
                }
            }
        }
    }

    /**
     * Method actually populating the widgets with the @param doctorModel
     * */
    private fun populateUi(doctorModel: DoctorModel?){
        doctorModel.let { doctor ->
            binding.apply {
                Glide.with(requireContext())
                    .load(doctor?.imageUrl)
                    .into(ivDoctor)

                tvDoctorName.text = doctor?.name
                tvTotalRatings.text = doctor?.portfolioModel?.totalRatings.toString()
                tvTotalReviews.text = getString(R.string.reviews_place_holder_string,doctor?.portfolioModel?.totalRatings)
                tvDoctorSpecialityAndPosting.text = getString(R.string.speciality_posting_place_holder_string,doctor?.speciality, doctor?.posting)
                tvTotalPatients.text = doctor?.portfolioModel?.numberOfPatients.toString()
                tvExperience.text = doctor?.portfolioModel?.experienceInYears.toString()
                tvRatingsOnCard.text = doctor?.portfolioModel?.totalRatings.toString()
                tvDoctorDesc.text = doctor?.aboutDoctor
                tvWorkingTime.text = doctor?.timings
            }
        }
    }


    /**
     * Method Containing all click Listeners
     * */
    private fun setupClickListeners(){
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().finish()
            }

            /*
            * Creating a book appointment and we will be filling it throughout the screens
            * */
            btnBookAppointment.setOnClickListener {

                if (appointmentDateAdapter.selectedItem != null){
                    bookingViewModel.onEvent(
                        SharedViewModelEvent.SaveDoctorDetails(
                        userId = 1,
                        doctorId = 2,
                        appointmentDate = appointmentDateAdapter.selectedItem!!
                    ))
                    return@setOnClickListener
                }

                showCustomSnackBarWithMessage("Something went wrong")
            }

            btnCalendar.setOnClickListener {
                requireActivity().supportFragmentManager.let {
                    getMaterialDataPicker().show(it,"DatePicker")
                }
            }
        }
    }

    private fun showCustomSnackBarWithMessage(message: String){
        val snackBar = Snackbar.make(binding.root,message, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(resources.getColor(R.color.snackbar_background_color))
        snackBar.show()
    }

    private fun getMaterialDataPicker() =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

}