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
import com.rydeit.io.databinding.ActivityResetPasswordBinding
import com.rydeit.io.helper.DialogHelper

class ResetPasswordActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityResetPasswordBinding
    lateinit var viewModel: ResetPasswordViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.passwordInputText.binding.editText,
            binding.confirmPasswordInputText.binding.editText
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)

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
        binding.loginTopBar.binding.navTopBar.backButton.setOnClickListener {
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

        // 完成密碼重設 button
        binding.resetButton.setOnClickListener {
            viewModel.resetPassword(editTextList[0].text.toString())

        }
    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputValid.observe(this) {
            binding.resetButton.isEnabled = it
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