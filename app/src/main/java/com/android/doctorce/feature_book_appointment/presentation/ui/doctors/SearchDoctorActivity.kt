package com.android.doctorce.feature_book_appointment.presentation.ui.doctors

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivitySearchDoctorsBinding
import com.android.doctorce.feature_book_appointment.domain.model.DoctorModel
import com.android.doctorce.feature_book_appointment.domain.util.DoctorOrder
import com.android.doctorce.feature_book_appointment.domain.util.OrderType
import com.android.doctorce.feature_book_appointment.presentation.ui.doctor_details.DoctorDetailsActivity
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

    private var feeFilter = true
    private var experienceFilter = false
    private var ratingsFilter = false

    private var ascendingFilter = true
    private var descendingFilter = false

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
                    binding.errorLayout.visibility = View.GONE
                }
                binding.filterChipGroup.isVisible = state.isFilterSectionVisible
                binding.sortChipGroup.isVisible = state.isFilterSectionVisible
            }
        }

        lifecycleScope.launchWhenStarted {
            // Observer Errors
            viewModel.infoChannel.collect { errorMessage ->
                binding.apply {
                    errorLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    btnFilter.visibility = View.GONE
                    textInputLayout.visibility = View.GONE
                }
                val snackBar = Snackbar.make(binding.root,errorMessage,Snackbar.LENGTH_SHORT)
                snackBar.view.setBackgroundColor(resources.getColor(R.color.snackbar_background_color))
                snackBar.show()
            }
        }
    }

    /**
     * Setting up chips and their onClick Listeners
     * */
    private fun setupChips(){
        binding.apply {
            filterChipGroup.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.feeChip -> {
                        feeFilter = true
                        experienceFilter = false
                        ratingsFilter = false
                        filterDoctors()
                    }
                    R.id.experienceChip -> {
                        feeFilter = false
                        experienceFilter = true
                        ratingsFilter = false
                        filterDoctors()
                    }
                    R.id.ratingsChip -> {
                        feeFilter = false
                        experienceFilter = false
                        ratingsFilter = true
                        filterDoctors()
                    }
                }

            }

            sortChipGroup.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.ascendingChip -> {
                        ascendingFilter = true
                        descendingFilter = false
                        filterDoctors()
                    }
                    R.id.descendingChip -> {
                        ascendingFilter = false
                        descendingFilter = true
                        filterDoctors()
                    }
                }
            }
        }
    }

    /**
     * Filters the doctors on the basis of flags set
     * Makes a new api call on the basis of these filters
     * */
    private fun filterDoctors(){
        if (feeFilter){
            if (ascendingFilter){
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Fee(OrderType.Ascending)))
            }else{
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Fee(OrderType.Descending)))
            }
        } else if (experienceFilter){
            if (ascendingFilter){
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Experience(OrderType.Ascending)))
            }else{
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Experience(OrderType.Descending)))
            }
        } else {
            if (ascendingFilter){
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Ratings(OrderType.Ascending)))
            }else{
                viewModel.onEvent(AllDoctorsEvent.Order(DoctorOrder.Ratings(OrderType.Descending)))
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
                finish()
            }
            btnFilter.setOnClickListener {
                toggleFilterButtonRotation()
                viewModel.onEvent(AllDoctorsEvent.ToggleOrderSectionVisibility)
            }
            errorIllustrationLayout.btnRetry.setOnClickListener {
                viewModel.onEvent(AllDoctorsEvent.Retry)
            }

        }
        setupChips()
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

    /**
     * toggles the filter section
     * if @param is true then expands
     * else collapses the section
     * */
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
        val doctorDescriptionIntent = Intent(this, DoctorDetailsActivity::class.java)
        val pair1 = Pair<View,String>(findViewById(R.id.ivDoctorImage),"doctor_image_transition")
        val pair2 = Pair<View,String>(findViewById(R.id.tvDoctorName),"doctor_name_transition")
        doctorDescriptionIntent.putExtra(DOCTOR,doctor)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2)
        startActivity(doctorDescriptionIntent,options.toBundle())
    }

    companion object {
        const val DOCTOR = "doctor"
    }
}