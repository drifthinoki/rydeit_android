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
import com.rydeit.io.databinding.ActivityRegisterStep3Binding
import com.rydeit.io.helper.DialogHelper

class RegisterStep3Activity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityRegisterStep3Binding
    lateinit var viewModel: RegisterStep3ViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.nicknameInputText.binding.editText,
            binding.passwordInputText.binding.editText,
            binding.confirmPasswordInputText.binding.editText,
            binding.referral.binding.editText
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterStep3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterStep3ViewModel::class.java)

        initView()
        initListener()
        registerLifeCycleObserver()
    }

    private fun initView() {
        binding.passwordCheckForLength.helperText.text = getString(R.string.password_more_than_8_digits)
        binding.passwordCheckForSymbol.helperText.text = getString(R.string.password_can_have_these_symbol)
    }

    private fun initListener() {
        // Top Bar 返回鍵
        binding.registerTopBar.binding.navTopBar.backButton.setOnClickListener {
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
                if (Constants.DEBUG) Log.e(TAG, "user input index: $index, value: $value" )
                viewModel.checkInputValue(index, value)
            }
        }

        // 完成註冊 button
        binding.finishRegisterButton.setOnClickListener {
            viewModel.registerStep3(editTextList[0].text.toString(), editTextList[1].text.toString(), editTextList[3].text.toString())

        }
    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputValid.observe(this) {
            binding.finishRegisterButton.isEnabled = it
        }

        viewModel.isVerifySuccess.observe(this) { isSuccessOrNull ->
            isSuccessOrNull?.let {
                if (it) {
                    DialogHelper.showDialog(this,
                        DialogHelper.DialogType.REGISTER_SUCCESS,
                    mainButtonAction = {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
                    })
                }
            }
        }

        viewModel.isPasswordLengthValid.observe(this) { isLengthOK ->
            updatePasswordCheckUI(isLengthOK, null)
        }

        viewModel.isPasswordSymbolValid.observe(this) { isSymbolOK ->
            updatePasswordCheckUI(null, isSymbolOK)
        }

        viewModel.isConfirmPasswordValid.observe(this) { isValid ->
            isValid?.let {
                binding.confirmPasswordInputText.updateBoxStrokeColor(isValid)
            }
        }
    }

    private fun updatePasswordCheckUI(isLengthOK: Boolean?, isSymbolOK: Boolean?) {
        isLengthOK?.let {
            if (it) {
                binding.passwordCheckForLength.imageCheck.setImageResource(R.drawable.ic_password_check_toggle)
                binding.passwordCheckForLength.helperText.setTextColor(resources.getColor(R.color.green_400, null))
            } else {
                binding.passwordCheckForLength.imageCheck.setImageResource(R.drawable.ic_password_check_default)
                binding.passwordCheckForLength.helperText.setTextColor(resources.getColor(R.color.gb_400, null))
            }
        }

        isSymbolOK?.let {
            if (it) {
                binding.passwordCheckForSymbol.imageCheck.setImageResource(R.drawable.ic_password_check_toggle)
                binding.passwordCheckForSymbol.helperText.setTextColor(resources.getColor(R.color.green_400, null))
            } else {
                binding.passwordCheckForSymbol.imageCheck.setImageResource(R.drawable.ic_password_check_default)
                binding.passwordCheckForSymbol.helperText.setTextColor(resources.getColor(R.color.gb_400, null))
            }
        }
    }
}