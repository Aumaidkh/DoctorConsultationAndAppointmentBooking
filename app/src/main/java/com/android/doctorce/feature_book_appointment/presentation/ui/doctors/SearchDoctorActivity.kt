package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivitySearchDoctorsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.appointment.DoctorDetailsActivity
import com.android.doctorce.feature_book_appointment.presentation.ui.doctors.adapters.DoctorAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "SearchDoctorActivity"
@AndroidEntryPoint
class SearchDoctorActivity : AppCompatActivity() {

    private var _binding: ActivitySearchDoctorsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllDoctorsViewModel by viewModels()

    private lateinit var doctorAdapter: DoctorAdapter
    private var isFilterIconRotated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_search_doctors)

        doctorAdapter = DoctorAdapter { doctor -> onDoctorClicked(doctor)}
        setupRecyclerView()
        setupObservers()
        setonClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    /**
     * Setting up observers like
     * 1. Populating the recyclerview here
     * 2. Consuming error flow as well
     * */
    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                if (!state.isLoading){
                    doctorAdapter.submitList(state.doctors)
                    binding.progressBar.visibility = View.GONE
                }
                binding.filterChipGroup.isVisible = state.isFilterSectionVisible
                binding.sortChipGroup.isVisible = state.isFilterSectionVisible
            }
        }

        lifecycleScope.launchWhenStarted {
            // Observer Errors
            viewModel.infoChannel.collect { errorMessage ->
                Snackbar.make(binding.root,errorMessage,Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Setting up the recyclerview only
     * 1. Assigning layout manager to it.
     * 2. Assigning adapter to the view.
     * */
    private fun setupRecyclerView() {
        binding.rvDoctors.apply {
            adapter = doctorAdapter
            layoutManager = GridLayoutManager(this@SearchDoctorActivity,2)
        }
    }

    /**
     * Setting button click listeners only
     * */
    private fun setonClickListeners(){
        binding.apply {
            btnBack.setOnClickListener {
                //finish()
                viewModel.onEvent(AllDoctorsEvent.Search("this"))
            }
            btnFilter.setOnClickListener {
                toggleFilterButtonRotation()
                viewModel.onEvent(AllDoctorsEvent.ToggleOrderSectionVisibility)
            }
        }
    }

    /**
     * Rotating filter icon when it is clicked
     * */
    private fun toggleFilterButtonRotation(){
        if (!isFilterIconRotated){
            binding.filterIcon.animate().rotation(90f).duration = 100
            expandFilterSection(true)
        } else {
            expandFilterSection(false)
            binding.filterIcon.animate().rotation(0f).duration = 100
        }
        isFilterIconRotated = !isFilterIconRotated
    }

    private fun expandFilterSection(isExpand: Boolean) {
        val anim = ValueAnimator.ofInt(
            binding.filterSection.measuredHeight,
            if (isExpand) 250 else 0
        )
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            val layoutParams = binding.filterSection.layoutParams
            layoutParams.height = value
            binding.filterSection.layoutParams = layoutParams
        }
        anim.duration = 100
        anim.start()
    }

    /**
     * 1. Navigating the user to the doctor details activity
     * 2. Passing the DoctorModel with the intent
     * */
    private fun onDoctorClicked(doctor: DoctorModel){
        val doctorDescriptionIntent = Intent(this,DoctorDetailsActivity::class.java)
        doctorDescriptionIntent.putExtra(DOCTOR,doctor)
        startActivity(doctorDescriptionIntent)
    }

    companion object {
        const val DOCTOR = "doctor"
    }
}