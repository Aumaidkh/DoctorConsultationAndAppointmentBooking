package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features.fragments.my_appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.doctorce.R
import com.android.doctorce.databinding.FragmentMyAppointmentsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MyAppointmentsFragment: Fragment() {

    private var _binding: FragmentMyAppointmentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyAppointmentsViewModel by viewModels()
    private lateinit var appointmentAdapter: AppointmentAdapter

    private var isUpcomingCardSelected = false
    private var isPastCardSelected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appointmentAdapter = AppointmentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointments, container, false)

        setupUpcomingAndPastButton()

        setupRecyclerView()

        setupObservers()

        setupOnClickListeners()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }


    private fun setupRecyclerView() {
        binding.rvAppointments.apply {
            adapter = appointmentAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (!it.isLoading) {
                    binding.apply {
                        progressBar3.visibility = View.GONE
                    }
                    appointmentAdapter.submitList(it.appointments)
                }
            }
        }
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
                    viewModel.onEvent(MyAppointmentsEvent.UpComingClick)
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
                    viewModel.onEvent(MyAppointmentsEvent.PastClick)
                } else {
                    pastCard.strokeWidth = 0
                }
            }
        }
    }
}