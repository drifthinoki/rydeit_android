package com.rydeit.io.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.R
import com.rydeit.io.config.Constants
import com.rydeit.io.databinding.ActivityForgetPasswordBinding
import com.rydeit.io.helper.DialogHelper

class ForgetPasswordActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityForgetPasswordBinding
    lateinit var viewModel: ForgetPasswordViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.emailInputText.binding.editText,
            binding.smsVerifyInputText.binding.editText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)

        initListener()
        registerLifeCycleObserver()
    }

    private fun initListener() {
        // Top Bar 返回鍵
        binding.loginTopBar.binding.navTopBar.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

        // 偵測所有的輸入框都有輸入內容 且 Checkbox checked
        // 將button狀態改為enable
        editTextList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged {
                val value = it.toString()
                if (Constants.DEBUG) Log.e(TAG, "user input index: $index, value: $value" )
                viewModel.updateInputValue(index, value)
            }
        }

        // 發送信箱驗證碼
        binding.smsVerifyInputText.onButtonClick = {
            viewModel.verifyEmail(editTextList[0].text.toString())
        }

        // 進行驗證 button
        binding.verifyButton.setOnClickListener {
            viewModel.verifyEmailCode(editTextList[0].text.toString(), editTextList[1].text.toString())

        }

        // 重新輸入信箱及手機 button
        binding.backToLoginButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

    }


    private fun registerLifeCycleObserver() {
        viewModel.isInputValid.observe(this) {
            binding.verifyButton.isEnabled = it
        }

        viewModel.isVerifySuccess.observe(this) { isSuccessOrNull ->
            isSuccessOrNull?.let {
                if (it) {
                    val intent = Intent(this, ResetPasswordActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
                } else {
                    clearInputText()
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