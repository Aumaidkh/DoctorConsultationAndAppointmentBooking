package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityDoctorDetailsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.BookAppointmentActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.doctors.SearchDoctorActivity.Companion.DOCTOR
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker

class DoctorDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDoctorDetailsBinding? = null
    private val binding get() = _binding!!

    var doctor: DoctorModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_details)

        doctor = intent?.getParcelableExtra(DOCTOR)
        populateUi(doctor)
        setupClickListeners()
    }

    private fun populateUi(doctor: DoctorModel?){
        doctor.let { doctor ->
            binding.apply {
                Glide.with(this@DoctorDetailsActivity)
                    .load(doctor?.imageUrl)
                    .into(ivDoctor)

                tvDoctorName.text = doctor?.name
                tvTotalRatings.text = doctor?.portfolioModel?.totalRatings.toString()
                tvTotalReviews.text = "(${doctor?.portfolioModel?.totalReviews.toString()} reviews)"
                tvDoctorSpecialityAndPosting.text = "${doctor?.speciality}.${doctor?.posting}"
                tvTotalPatients.text = doctor?.portfolioModel?.numberOfPatients.toString()
                tvExperience.text = doctor?.portfolioModel?.experienceInYears.toString()
                tvRatingsOnCard.text = doctor?.portfolioModel?.totalRatings.toString()
                tvDoctorDesc.text = doctor?.aboutDoctor
            }
        }
    }

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