package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityDoctorDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.BookAppointmentModel
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.AddPatientDetailsActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.common.ProcessBooking
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModelEvent
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.BookAppointmentActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.adapters.AppointmentDateAdapter
import com.android.doctorce.feature_book_appointment.presentation.util.Constants.NEW_APPOINTMENT
import com.android.doctorce.feature_book_appointment.presentation.util.HelperMethods.getCalculatedDate
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.util.*

private const val TAG = "DoctorDetailsActivity"
@AndroidEntryPoint
class DoctorDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDoctorDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorDetailsViewModel by viewModels()
    private val bookingViewModel: SharedViewModel by viewModels()

    private lateinit var appointmentDateAdapter: AppointmentDateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_details)

        // Receive doctor id from previous activity and make a post request
       // doctor = intent?.getParcelableExtra(DOCTOR)
       // populateUi(doctor)
        appointmentDateAdapter = AppointmentDateAdapter()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setupAppointmentDateRecyclerView()
        setupObservers()
    }

    private fun setupAppointmentDateRecyclerView(){
        binding.rvDateAndDay.apply {
            adapter = appointmentDateAdapter
            layoutManager = LinearLayoutManager(this@DoctorDetailsActivity,HORIZONTAL, false)
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
                        val addPatientDetailsIntent = Intent(this@DoctorDetailsActivity, AddPatientDetailsActivity::class.java)
                        startActivity(addPatientDetailsIntent)
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
                Glide.with(this@DoctorDetailsActivity)
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
                finish()
            }

            /*
            * Creating a book appointment and we will be filling it throughout the screens
            * */
            btnBookAppointment.setOnClickListener {

                if (appointmentDateAdapter.selectedItem != null){
                    bookingViewModel.onEvent(SharedViewModelEvent.SaveDoctorDetails(
                        userId = 1,
                        doctorId = 2,
                        appointmentDate = appointmentDateAdapter.selectedItem!!
                    ))
                    return@setOnClickListener
                }

                showCustomSnackBarWithMessage("Something went wrong")
//                val newAppointment = BookAppointmentModel(
//                    userId = 1, // has to be passed from prefs
//                    doctorId = 2, // will be passed from prevActivity
//                    appointmentDate = appointmentDateAdapter.selectedItem
//                )
               // val addPatientDetailsIntent = Intent(this@DoctorDetailsActivity, AddPatientDetailsActivity::class.java)
               // addPatientDetailsIntent.putExtra(NEW_APPOINTMENT,newAppointment)
               // startActivity(addPatientDetailsIntent)
            }

            btnCalendar.setOnClickListener {
                supportFragmentManager.let {
                    getMaterialDataPicker().show(it,"DatePicker")
                }
            }
        }
    }

    private fun showCustomSnackBarWithMessage(message: String){
        val snackBar = Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(getColor(R.color.snackbar_background_color))
        snackBar.show()
    }

    private fun getMaterialDataPicker() =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
}