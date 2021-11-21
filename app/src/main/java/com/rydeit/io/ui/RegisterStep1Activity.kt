package com.rydeit.io.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.R
import com.rydeit.io.config.Constants
import com.rydeit.io.databinding.ActivityRegisterStep1Binding
import com.rydeit.io.helper.DialogHelper
import com.rydeit.io.helper.PinHelper
import com.rydeit.io.helper.PinInputType

/**
 * 註冊第一階段
 * 用戶輸入信箱, 手機進行註冊
 */
class RegisterStep1Activity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityRegisterStep1Binding
    lateinit var viewModel: RegisterStep1ViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.emailInputText.binding.editText,
            binding.phoneInputText.binding.editText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterStep1ViewModel::class.java)

        initListener()
        registerLifeCycleObserver()
    }


    private fun initListener() {
        // Top Bar 返回鍵
        binding.registerTopBar.binding.navTopBar.backButton.setOnClickListener {
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

        binding.checkboxAgreePrivacyTerm.setOnCheckedChangeListener { buttonView, isChecked ->
            // unchecked 回傳空字串
            val value = if (isChecked) "not empty" else ""
            viewModel.updateInputValue(2, value)
        }

        // 進行驗證 button
        binding.nextButton.setOnClickListener {
            viewModel.register(editTextList[0].text.toString(), editTextList[1].text.toString())
        }

        // 前往登入 button
        binding.goLoginButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }
    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputValid.observe(this) {
            binding.nextButton.isEnabled = it
        }

        viewModel.isRegisterSuccess.observe(this) { isSuccessOrNull ->
            isSuccessOrNull?.let {
                if (it) {
                    val intent = Intent(this, RegisterStep2Activity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
                } else {
                    // TODO: 第一階段註冊失敗
//                    DialogHelper.showDialog(this, DialogHelper.DialogType.VERIFY_FAIL)
//                    clearInputText()
                }
            }

        }
    }
}