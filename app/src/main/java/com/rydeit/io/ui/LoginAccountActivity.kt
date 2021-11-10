package com.rydeit.io.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.LoginAccountItemType
import com.rydeit.io.LoginAccountViewModel
import com.rydeit.io.R
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.databinding.ActivityLoginAccountBinding
import com.rydeit.io.helper.DialogHelper
import com.rydeit.io.helper.UserHelper


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
            viewModel.login(binding.checkboxRememberEmail.isChecked)
        }

    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputTextValid.observe(this) {
            binding.nextButton.isEnabled = it
        }

        viewModel.isLoginSuccess.observe(this) { isSuccess ->
            isSuccess?.let {
                if (!it) {
                    DialogHelper.showDialog(this, DialogHelper.DialogType.LOGIN_FAIL)
                    binding.emailCustomTextInputLayout.binding.editText.text?.clear()
                    binding.passwordTextInputEdittext.text?.clear()
                } else {
                    val intent = Intent(this, LoginVerifyActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        UserHelper.user.observe(this) { user ->
            user?.let {
                if (user.rememberEmail) {
                    binding.emailCustomTextInputLayout.binding.editText.setText(user.email)
                }
            }
        }
    }

}