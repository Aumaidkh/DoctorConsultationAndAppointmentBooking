package com.android.doctorce.presentation.ui.sign_up_process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityForgetPasswordBinding? = null
    private val binding get() = _binding!!

    /**
     * Card state variables
     * */
    private var isPhoneCardSelected = false
    private var isEmailCardSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)

        binding.apply {
            phoneCard.setOnClickListener {
                isPhoneCardSelected = !isPhoneCardSelected
                if (isPhoneCardSelected){
                    phoneCard.strokeWidth = 2
                } else {
                    phoneCard.strokeWidth = 0
                }
            }

            emailCard.setOnClickListener {
                isEmailCardSelected = !isEmailCardSelected
                if (isEmailCardSelected){
                    emailCard.strokeWidth = 2
                } else {
                    emailCard.strokeWidth = 0
                }
            }

            btnContinue.setOnClickListener {
                startActivity(Intent(this@ForgetPasswordActivity,NewPasswordSetActivity::class.java))
            }
        }
    }
}