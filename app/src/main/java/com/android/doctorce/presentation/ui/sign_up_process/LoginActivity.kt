package com.android.doctorce.presentation.ui.sign_up_process

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.doctorce.R
import com.android.doctorce.databinding.ActivityLoginBinding
import com.android.doctorce.presentation.ui.home_and_key_features.HomeActivity

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.btnForgetPassword.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
        }

    }
}