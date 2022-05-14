package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityDoctorDetailsBinding
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.BookAppointmentActivity
import com.google.android.material.datepicker.MaterialDatePicker

class DoctorDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDoctorDetailsBinding? = null
    private val binding get() = _binding!!

    /*Pickers*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_details)

        setupClickListeners()
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