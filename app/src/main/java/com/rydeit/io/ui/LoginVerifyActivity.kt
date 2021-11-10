package com.rydeit.io.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityLoginVerifyBinding

class LoginVerifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginVerifyBinding
    lateinit var viewModel: LoginVerifyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginVerifyViewModel::class.java)

        initListener()

    }

    private fun initListener() {
        binding.topBarLogin.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

        binding.emailVerifyInputText.binding.editTextButton.setOnClickListener {
            viewModel.loginVerifyEmail()
        }
    }
}