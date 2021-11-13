package com.rydeit.io.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityPinBinding
import com.rydeit.io.helper.PinInputType
import com.rydeit.io.helper.PinHelper
import com.rydeit.io.helper.UserHelper

class PinActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    lateinit var binding: ActivityPinBinding
    lateinit var viewModel: PinViewModel

    private val imageIndicators by lazy {
        listOf(
            binding.imageIndicator1,
            binding.imageIndicator2,
            binding.imageIndicator3,
            binding.imageIndicator4 )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pinInputType = intent.getSerializableExtra(PinHelper.KEY_PIN_INPUT_TYPE) as PinInputType

        viewModel = ViewModelProvider(this, PinViewModelFactory(pinInputType)).get(PinViewModel::class.java)

        binding.viewModel = viewModel

        initListener()
        registerLifeCycleObserver()
    }

    private fun initListener() {
        binding.reLoginButton.setOnClickListener {
            reLogin()
        }
    }

    private fun registerLifeCycleObserver() {

        viewModel.pinInputTypeLiveData.observe(this) {
            changePinInputTypeTo(it)
        }

        viewModel.userInputNumber.observe(this) {
            it?.let {
                val index = it.index
                val number = it.number
//                if (DEBUG) Log.e(TAG, "User Input Number index: ${index}, number: $number")
//                if (DEBUG) Log.e(TAG, "pin: ${viewModel.wholePinString}")
                updateImageIndicatorsUI(index, number)
            }
        }

        viewModel.isPassVerification.observe(this) {
            if (it == null) return@observe

            if (it.isPass) {
                when(it.currentPinInputType) {
                    PinInputType.SET_NEW_CONFIRM -> {
                        // 設定 Pin 碼成功
                        // 變更登入狀態
                        UserHelper.login()
                        // 進入首頁
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                    PinInputType.CLOSE_PW_PROTECT -> passwordProtect(false)
                    PinInputType.MODIFY_PW -> {
                        changePinInputTypeTo(PinInputType.SET_NEW)
                        return@observe
                    }
                    else -> {}
                }
                finish()
            } else {
                when(it.currentPinInputType) {
                    PinInputType.ENTER_APP -> {
                        // 達到錯誤上限
                        // Pin 碼錯誤累積達 5 次，會登出帳戶，跳轉至登入畫面，用戶需重新登入
                        if (viewModel.isReachErrorLimit) {
                            reLogin()
                            return@observe
                        }
                        // 顯示剩餘嘗試次數
                        binding.tvErrorMsg.text = viewModel.getErrMsg()

                    }
                }
                viewModel.clearPin()
            }

        }
    }

    private fun updateImageIndicatorsUI(index:Int, string: String) {
        if (string.length != 1) throw IllegalArgumentException("Input number size over limit..")
        val resId = if (string == "_") R.drawable.light_blue_round_stroke else R.drawable.light_blue_round
        imageIndicators[index].setImageResource(resId)
    }

    private fun passwordProtect(isActivated:Boolean) {
//        Utils.setBooleanSet(this, Constants.IS_PW_PROTECT_ACTIVATED, isActivated)
//        Utils.setBooleanSet(this, Constants.IS_BIOMETRIC_AUTH_ACTIVATED, isActivated)
//        val toastText = if (isActivated) "密碼保護設定完成" else "密碼保護功能已關閉"
//        Utils.showNormalToast(this, toastText)
    }

    private fun changePinInputTypeTo(pinInputType: PinInputType) {
        binding.loginTopBar.binding.title.text = getString(pinInputType.title)
        binding.tvSubtitle.text = getString(pinInputType.subTitle)
        binding.tvErrorMsg.visibility = if (pinInputType.isShowErrorMsg) View.VISIBLE else View.GONE
        binding.resetPinButton.visibility = if (pinInputType.isShowResetPinButton) View.VISIBLE else View.GONE
        binding.clReLogin.visibility = if (pinInputType.isShowReLoginButton) View.VISIBLE else View.GONE
        binding.loginTopBar.binding.backButton.visibility = if (pinInputType.isShowBackButton) View.VISIBLE else View.GONE
    }

    private fun reLogin() {
        UserHelper.logout()
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}