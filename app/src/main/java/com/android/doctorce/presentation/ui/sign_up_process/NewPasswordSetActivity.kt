package com.android.doctorce.presentation.ui.sign_up_process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityNewPasswordSetBinding

class NewPasswordSetActivity : AppCompatActivity() {
    private var _binding: ActivityNewPasswordSetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_new_password_set)


        binding.apply {
            btnConfirm.setOnClickListener {
                startActivity(Intent(applicationContext,VerifyOtpActivity::class.java))
            }
        }
    }
}