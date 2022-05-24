package com.android.doctorce.feature_book_appointment.presentation.ui.appointment.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityBookAppointmentBinding
import com.android.doctorce.databinding.FragmentAppointmentTypeSelectionBinding
import com.android.doctorce.feature_book_appointment.presentation.ui.common.SharedViewModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_counseling.PaymentSuccessActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "AppointmentTypeSelectio"
@AndroidEntryPoint
class AppointmentTypeSelectionFragment : Fragment() {

    private var _binding: FragmentAppointmentTypeSelectionBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_appointment_type_selection,container,false)
        setupOnClickListeners()
        return binding.root
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
            requireActivity().startActivity(Intent(context, PaymentSuccessActivity::class.java))
        }
    }

}