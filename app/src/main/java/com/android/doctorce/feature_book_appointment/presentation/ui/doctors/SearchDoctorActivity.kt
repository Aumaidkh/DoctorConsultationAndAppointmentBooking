package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivitySearchDoctorsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.presentation.ui.doctors.adapters.DoctorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "SearchDoctorActivity"
@AndroidEntryPoint
class SearchDoctorActivity : AppCompatActivity() {

    private var _binding: ActivitySearchDoctorsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllDoctorsViewModel by viewModels()

    private lateinit var doctorAdapter: DoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_search_doctors)

        doctorAdapter = DoctorAdapter { doctor -> onDoctorClicked(doctor)}
        setupRecyclerView()
        setonClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                if (!state.isLoading){
                    doctorAdapter.submitList(state.doctors)
                }else{
                    Log.d(TAG, "Loading")
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvDoctors.apply {
            adapter = doctorAdapter
            layoutManager = GridLayoutManager(this@SearchDoctorActivity,2)
        }
    }

    private fun setonClickListeners(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun onDoctorClicked(doctor: DoctorModel){

    }
}