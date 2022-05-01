package com.android.doctorce.presentation.ui.doctor_counseling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityBookAppointmentBinding

class BookAppointmentActivity : AppCompatActivity() {
    private var _binding: ActivityBookAppointmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_book_appointment)

        setupOnClickListeners()
    }

    private fun setupOnClickListeners(){
        binding.btnPayNow.setOnClickListener{
            startActivity(Intent(this, PaymentSuccessActivity::class.java))
        }
    }
}