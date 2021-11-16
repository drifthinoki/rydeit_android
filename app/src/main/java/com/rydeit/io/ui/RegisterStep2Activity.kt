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
import com.rydeit.io.databinding.ActivityRegisterStep2Binding
import com.rydeit.io.helper.DialogHelper

/**
 * 註冊第二階段
 * 檢查電子信箱, 簡訊驗證碼
 */
class RegisterStep2Activity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityRegisterStep2Binding
    lateinit var viewModel: RegisterStep2ViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.emailVerifyInputText.binding.editText,
            binding.phoneVerifyInputText.binding.editText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterStep2ViewModel::class.java)

        initListener()
        registerLifeCycleObserver()
    }


    private fun initListener() {
        // Top Bar 返回鍵
        binding.registerTopBar.binding.backButton.setOnClickListener {
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
        binding.emailVerifyInputText.onButtonClick = {
            viewModel.verifyEmail()
        }

        // 發送簡訊驗證碼
        binding.phoneVerifyInputText.onButtonClick = {
            viewModel.verifyPhone()
        }

        // 進行驗證 button
        binding.verifyButton.setOnClickListener {
            viewModel.registerStep2(editTextList[0].text.toString(), editTextList[1].text.toString())

        }

        // 重新輸入信箱及手機 button
        binding.reenterEmailPhoneButton.setOnClickListener {
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
                    val intent = Intent(this, RegisterStep3Activity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)

                } else {
                    DialogHelper.showDialog(this, DialogHelper.DialogType.VERIFY_FAIL)
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