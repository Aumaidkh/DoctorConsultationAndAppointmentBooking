package com.android.doctorce.presentation.ui.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivitySearchDoctorsBinding

class SearchDoctorActivity : AppCompatActivity() {
    private var _binding: ActivitySearchDoctorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_search_doctors)

        setonClickListeners()
    }

    private fun setonClickListeners(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}