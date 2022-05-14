package com.android.doctorce.feature_book_appointment.presentation.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile)

        setupButtonListeners()

        setupGenderMenu()
    }

    private fun setupButtonListeners(){
        binding.apply {
            // Back
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupGenderMenu() {
        val items = listOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.genderMenu as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.male_female_menu,menu)
        return true
    }
}