package com.rydeit.io.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.LoginViewModel
import com.rydeit.io.R
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.databinding.ActivityLoginBinding
import com.rydeit.io.helper.DialogHelper
import com.rydeit.io.helper.UserHelper


class LoginActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.emailTextInputLayout.binding.editText,
            binding.passwordTextInputLayout.binding.editText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(
            LoginViewModel::class.java)

        initListener()
        registerLifeCycleObserver()
    }

    private fun initListener() {
        // 返回鍵
        binding.loginTopBar.binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

        // 偵測所有的輸入框都有輸入內容
        // 將button狀態改為enable
        editTextList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged {
                val value = it.toString()
                if (DEBUG) Log.e(TAG, "user input index: $index, value: $value" )
                viewModel.updateInputValue(index, value)
            }
        }

        // 繼續按鈕
        binding.nextButton.setOnClickListener {
            viewModel.login(editTextList[0].text.toString(), editTextList[1].text.toString(), binding.checkboxRememberEmail.isChecked)
        }

        // 註冊帳號按鈕
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterStep1Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
        }

    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputValid.observe(this) {
            binding.nextButton.isEnabled = it
        }

        viewModel.isLoginSuccess.observe(this) { isSuccess ->
            isSuccess?.let {
                if (!it) {
                    DialogHelper.showDialog(this, DialogHelper.DialogType.LOGIN_FAIL)
                    clearInputText()
                } else {
                    val intent = Intent(this, Login2faActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        UserHelper.user.observe(this) { user ->
            user?.let {
                if (user.isRememberEmail) {
                    binding.emailTextInputLayout.binding.editText.setText(user.email)
                    binding.checkboxRememberEmail.isChecked = user.isRememberEmail
                }
            }
        }
    }

    private fun clearInputText() {
        editTextList.forEach { editText ->
            editText.text.clear()
        }
    }

}