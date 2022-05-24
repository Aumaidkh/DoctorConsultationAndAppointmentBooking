package com.android.doctorce.feature_book_appointment.presentation.ui.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.doctorce.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
    }
}