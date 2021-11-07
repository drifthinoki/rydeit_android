package com.progressingtoday.rydeit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.progressingtoday.rydeit.LoginAccountItemType
import com.progressingtoday.rydeit.LoginAccountViewModel
import com.progressingtoday.rydeit.R
import com.progressingtoday.rydeit.config.Constants.DEBUG
import com.progressingtoday.rydeit.databinding.ActivityLoginAccountBinding


class LoginAccountActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    lateinit var binding: ActivityLoginAccountBinding

    lateinit var viewModel: LoginAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(
            LoginAccountViewModel::class.java)

        initListener()
        registerLifeCycleObserver()
    }

    private fun initListener() {
        binding.topBarLoginAccount.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

        val emailInputTextLayout = binding.emailCustomTextInputLayout.binding.textInputLayout

        emailInputTextLayout.editText?.doAfterTextChanged {
            val email = it.toString()
            if (DEBUG) Log.e(TAG, "input text email: $email")
            viewModel.updateLoginAccountItem(LoginAccountItemType.EMAIL, email)
        }

        val passwordInputTextLayout = binding.passwordTextInputLayout

        passwordInputTextLayout.editText?.doAfterTextChanged {
            val password = it.toString()
            if (DEBUG) Log.e(TAG, "input text password: $password")
            viewModel.updateLoginAccountItem(LoginAccountItemType.PASSWORD, password)
        }

        binding.nextButton.setOnClickListener {
            viewModel.login()
        }

    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputTextValid.observe(this) {
            binding.nextButton.isEnabled = it
        }
    }

}