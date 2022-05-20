package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityDoctorDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.BookAppointmentActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.adapters.AppointmentDateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DoctorDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDoctorDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoctorDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_details)

        // Receive doctor id from previous activity and make a post request
       // doctor = intent?.getParcelableExtra(DOCTOR)
       // populateUi(doctor)
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setupAppointmentDateRecyclerView()
        setupObservers()
    }

    private fun setupAppointmentDateRecyclerView(){
        val appointmentDateAdapter = AppointmentDateAdapter()
        binding.rvDateAndDay.apply {
            adapter = appointmentDateAdapter
        }
        appointmentDateAdapter.submitList(listOf(
            AppointmentDateUiState("Mon","16",true),
            AppointmentDateUiState("Tue","17",false),
            AppointmentDateUiState("Wed","18",false),
            AppointmentDateUiState("Thu","19",false),
            AppointmentDateUiState("Fri","20",false),
            AppointmentDateUiState("Sat","21",false),
        ))
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
                val snackBar = Snackbar.make(binding.root,error,Snackbar.LENGTH_SHORT)
                snackBar.view.setBackgroundColor(getColor(R.color.snackbar_background_color))
                snackBar.show()
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

            btnBookAppointment.setOnClickListener {
                startActivity(Intent(this@DoctorDetailsActivity, BookAppointmentActivity::class.java))
            }

            btnCalendar.setOnClickListener {
                supportFragmentManager.let {
                    getMaterialDataPicker().show(it,"DatePicker")
                }
            }
        }
    }

    private fun getMaterialDataPicker() =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
}