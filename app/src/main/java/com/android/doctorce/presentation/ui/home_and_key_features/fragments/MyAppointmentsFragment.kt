package com.android.doctorce.presentation.ui.home_and_key_features.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.doctorce.R
import com.android.doctorce.databinding.FragmentMyAppointmentsBinding

class MyAppointmentsFragment: Fragment() {

    private var _binding: FragmentMyAppointmentsBinding? = null
    private val binding get() = _binding!!

    private var isUpcomingCardSelected = false
    private var isPastCardSelected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointments, container, false)

        setupUpcomingAndPastButton()

        setupOnClickListeners()

        return binding.root
    }

    private fun setupOnClickListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupUpcomingAndPastButton(){
        binding.apply {
            upcomingCard.setOnClickListener {
                isUpcomingCardSelected = !isUpcomingCardSelected
                if (isUpcomingCardSelected){
                    /*
                    * Deselect Past card
                    * */
                    isPastCardSelected = false
                    upcomingCard.strokeWidth = 4
                } else {
                    upcomingCard.strokeWidth = 0
                }
            }

            pastCard.setOnClickListener {
                isPastCardSelected = !isPastCardSelected
                if (isPastCardSelected){
                    /*
                    * Deselect Upcoming card
                    * */
                    isUpcomingCardSelected = false
                    pastCard.strokeWidth = 4
                } else {
                    pastCard.strokeWidth = 0
                }
            }
        }
    }
}