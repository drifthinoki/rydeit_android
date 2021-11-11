package com.rydeit.io.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.rydeit.io.R
import com.rydeit.io.config.Constants
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.ui.PinActivity
import com.rydeit.io.utils.StorageUtil
import java.lang.IllegalStateException


/**
 * 顯示不同的View
 * 1.設定新密碼
 * 2.再次確認新密碼
 * 3.驗證密碼-進入APP
 * 4.驗證密碼-關閉密碼保護
 * 5.驗證密碼-修改密碼
 */
enum class PinInputType {
    SET_NEW, SET_NEW_CONFIRM, ENTER_APP, CLOSE_PW_PROTECT, MODIFY_PW;

    val title:Int
        get() {
            return when(this) {
                SET_NEW, SET_NEW_CONFIRM -> R.string.pin_title_set_pin
                ENTER_APP -> R.string.pin_title_enter_pin_to_unlock
                MODIFY_PW, CLOSE_PW_PROTECT -> throw IllegalStateException()
            }
        }

    val subTitle:Int
        get() {
            return when(this) {
                SET_NEW -> R.string.pin_subtitle_set_pin
                SET_NEW_CONFIRM -> R.string.pin_subtitle_set_pin_again
                ENTER_APP -> R.string.pin_subtitle_enter_your_pin
                MODIFY_PW, CLOSE_PW_PROTECT -> throw IllegalStateException()
            }
        }

    val isShowBackButton: Boolean
        get() {
            return when(this) {
                SET_NEW, SET_NEW_CONFIRM, CLOSE_PW_PROTECT, MODIFY_PW -> true
                ENTER_APP -> false
            }
        }

    val isShowErrorMsg: Boolean
        get() {
            return when(this) {
                ENTER_APP -> true
                SET_NEW, SET_NEW_CONFIRM, CLOSE_PW_PROTECT, MODIFY_PW -> false
            }
        }

    val isShowResetPinButton: Boolean
        get() {
            return when(this) {
                SET_NEW, SET_NEW_CONFIRM -> true
                ENTER_APP, CLOSE_PW_PROTECT, MODIFY_PW -> false
            }
        }

    val isShowReLoginButton: Boolean
        get() {
            return when(this) {
                ENTER_APP -> true
                SET_NEW, SET_NEW_CONFIRM, CLOSE_PW_PROTECT, MODIFY_PW -> false
            }
        }

    val canUseBiometricAuth:Boolean
        get() {
            return when(this) {
                ENTER_APP, CLOSE_PW_PROTECT -> true
                SET_NEW, SET_NEW_CONFIRM, MODIFY_PW -> false
            }
        }
}

object PinHelper {

    private val TAG = this::class.java.simpleName

    const val KEY_PIN_INPUT_TYPE = "pin_input_type"
    var isPwProtectActivated:Boolean
        get() = true
        set(value) {
//            Utils.setBooleanSet(Constants.IS_PW_PROTECT_ACTIVATED, value)
//
//            //關閉密碼保護的時候，也要關閉TouchID/FaceID
//            if (!value)
//                BiometricAuthHelper.isBiometricAuthActivated = false
        }

    var needConfirmedPin:String? = null

    var tryErrorTimesLeft = Constants.PIN_ERROR_TIMES
    val tryErrorMsg: String
        get() {
            val triedTimes = Constants.PIN_ERROR_TIMES - tryErrorTimesLeft
            return if (triedTimes > 0)  "輸入錯誤 $triedTimes 次，剩餘次數 $tryErrorTimesLeft 次" else ""
        }
    val isReachErrorLimit: Boolean
        get() = tryErrorTimesLeft <= 0

    private var isPwVerified = false

    val isPasswordVerified:Boolean
        get() = !isPwProtectActivated || isPwVerified


    fun showPinInputView(context: Context, pinInputType: PinInputType) {
        when(pinInputType) {
            PinInputType.SET_NEW, PinInputType.CLOSE_PW_PROTECT -> {
                context as Activity
                val intent = Intent(context, PinActivity::class.java)
                intent.putExtra(KEY_PIN_INPUT_TYPE, pinInputType)
                //TODO need to update intent code
                context.startActivityForResult(intent, 1001)
            }
            PinInputType.MODIFY_PW -> {
                val intent = Intent(context, PinActivity::class.java)
                intent.putExtra(KEY_PIN_INPUT_TYPE, pinInputType)
                context.startActivity(intent)
            }
            PinInputType.ENTER_APP -> {
                if (!isPwProtectActivated) return
                val intent = Intent(context, PinActivity::class.java)
                intent.putExtra(KEY_PIN_INPUT_TYPE, pinInputType)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
            else -> throw IllegalArgumentException("Haven't determined")
        }

    }

    /**
     * 設定新 Pin 碼
     */
    fun setNewPin(pin: String) {
        needConfirmedPin = pin
        if (DEBUG) Log.e(TAG, "SET_NEW pin: $pin")
    }

    /**
     * 驗證新設定的 Pin 碼
     */
    fun verifyNewPin(pin: String): Boolean {

        if (DEBUG) Log.e(TAG, "SET_NEW_CONFIRM correct pin: $needConfirmedPin, user input pin: $pin")

        return verify(needConfirmedPin, pin,
            passAction = { StorageUtil.storeString(Constants.PREF_PIN, pin) })
    }

    /**
     * 驗證 Pin 碼
     */
    fun verifyPin(pin:String):Boolean {

        val correctPin = StorageUtil.getString(Constants.PREF_PIN)

        if (DEBUG) Log.e(TAG, "ENTER_APP correct pin: $correctPin, user input pin: $pin")

        return verify(correctPin, pin,
            notPassAction = {tryErrorTimesLeft -= 1})
    }

    private fun verify(correctPin: String?, pin: String, passAction:(()->Unit)? = null, notPassAction:(()->Unit)? = null): Boolean {
        correctPin?.let {
            val isPass = it == pin
            if (isPass) passAction?.invoke() else notPassAction?.invoke()
            return isPass

        } ?: throw IllegalStateException()
    }
}