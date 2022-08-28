package com.android.doctorce.feature_book_appointment.presentation.ui.home_and_key_features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.doctorce.R
import com.android.doctorce.core.InternetAwareActivity
import com.android.doctorce.core.InternetAwareActivityImpl
import com.android.doctorce.databinding.ActivityHomeBinding
import com.android.doctorce.feature_book_appointment.presentation.core.InternetAwareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), InternetAwareActivity by InternetAwareActivityImpl() {
    /**
     * Binding */
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val internetAwareViewModel: InternetAwareViewModel by viewModels()



    /**
     * Nav Controller
     * */
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        /**
         * Calling Internet Connectivity Logic here
         * */
        handleInternetConnection(
            binding.tvConnectionStatus,
            internetAwareViewModel,
            lifecycleScope
        )
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