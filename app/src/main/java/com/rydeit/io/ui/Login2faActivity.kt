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
import com.rydeit.io.databinding.ActivityLogin2faBinding
import com.rydeit.io.helper.DialogHelper
import com.rydeit.io.helper.PinInputType
import com.rydeit.io.helper.PinHelper

class Login2faActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    lateinit var binding: ActivityLogin2faBinding
    lateinit var viewModel: Login2faViewModel
    private val editTextList: List<EditText> by lazy {
        listOf(
            binding.emailVerifyInputText.binding.editText,
            binding.smsVerifyInputText.binding.editText,
            binding.google2faInputText.binding.editText)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogin2faBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(Login2faViewModel::class.java)

        initListener()
        registerLifeCycleObserver()

    }

    private fun initListener() {
        // Top Bar 返回鍵
        binding.loginTopBar.binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }

        // 發送信箱驗證碼
        binding.emailVerifyInputText.binding.editTextButton.setOnClickListener {
            viewModel.loginVerifyEmail()
        }

        // 發送簡訊驗證碼
        binding.smsVerifyInputText.binding.editTextButton.setOnClickListener {
            viewModel.loginVerifyPhone()
        }

        // 偵測所有的輸入框都有輸入內容後，將進行驗證的button狀態改為enable
        editTextList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged {
                val code = it.toString()
                val type= Login2faType.values()[index]
                if (Constants.DEBUG) Log.e(TAG, "$type verify code: $code" )
                viewModel.updateLogin2faItem(type, code)
            }
        }

        // 進行驗證 button
        binding.verifyButton.setOnClickListener {
            viewModel.login2FA(editTextList[0].text.toString(), editTextList[1].text.toString(), editTextList[2].text.toString())
        }

        // 重新輸入信箱及密碼 button
        binding.reenterEmailPasswordButton.setOnClickListener {
            finishWithAnimation()
        }
    }

    private fun clearInputText() {
        editTextList.forEach { editText ->
            editText?.text.clear()
        }
    }

    private fun registerLifeCycleObserver() {
        viewModel.isInputTextValid.observe(this) {
            binding.verifyButton.isEnabled = it
        }

        viewModel.isLogin2faSuccess.observe(this) { isSuccessOrNull ->
            isSuccessOrNull?.let {
                if (it) {
                    PinHelper.showPinInputView(this, PinInputType.SET_NEW)
                } else {
                    DialogHelper.showDialog(this, DialogHelper.DialogType.VERIFY_FAIL)
                    clearInputText()
                }
            }

        }
    }

    private fun finishWithAnimation() {
        finish()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
    }
}