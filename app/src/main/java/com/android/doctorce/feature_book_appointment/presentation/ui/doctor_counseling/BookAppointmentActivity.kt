package com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityBookAppointmentBinding
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import kotlinx.coroutines.flow.collect

private const val TAG = "BookAppointmentActivity"
class BookAppointmentActivity : AppCompatActivity() {
    private var _binding: ActivityBookAppointmentBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_book_appointment)

        setupOnClickListeners()

    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setupObservers(){
        lifecycleScope.launchWhenStarted {
            bookingViewModel.bookingState.collect { booking ->
                Log.d(TAG, "setupObservers: $booking")
            }
        }
    }

    private fun setupOnClickListeners(){
        binding.btnPayNow.setOnClickListener{
            startActivity(Intent(this, PaymentSuccessActivity::class.java))
        }
    }
}