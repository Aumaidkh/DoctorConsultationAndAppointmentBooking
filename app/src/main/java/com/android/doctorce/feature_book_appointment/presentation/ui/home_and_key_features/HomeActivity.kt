package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    /**
     * Binding */
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    /**
     * Nav Controller
     * */
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        setupBottomNavigationMenuWithNavController()


    }

    private fun setupBottomNavigationMenuWithNavController() {
        navController = findNavController(R.id.navHostFragment)
        binding.bottomNavMenu.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}