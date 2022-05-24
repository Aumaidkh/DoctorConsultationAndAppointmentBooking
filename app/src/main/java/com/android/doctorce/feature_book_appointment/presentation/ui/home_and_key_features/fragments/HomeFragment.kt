package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.doctorce.R
import com.android.doctorce.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setupOnClickListeners()

        return binding.root
    }

    private fun setupOnClickListeners() {
        binding.apply {
            toolbar.btnSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchDoctorActivity)
            }

            toolbar.btnNotifications.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_appointmentActivity)
            }
        }
    }
}