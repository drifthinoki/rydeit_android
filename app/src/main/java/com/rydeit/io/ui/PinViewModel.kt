package com.rydeit.io.ui

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rydeit.io.config.Constants
import com.rydeit.io.helper.PinInputType
import com.rydeit.io.helper.PinHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PinViewModel(var pinInputType: PinInputType): ViewModel() {

    private val CONFIRM_PASSWORD_DELAY_TIME = 300L

    var pinInputTypeLiveData: MutableLiveData<PinInputType> = MutableLiveData(pinInputType)

    // 用戶輸入的 Pin 碼
    var userInputNumber: MutableLiveData<UserInputNumber?> = MutableLiveData(null)

    // 用戶輸入的 Pin 碼是否正確 (適用 1.SET_NEW_CONFIRM 2.ENTER_APP)
    var isPassVerification: MutableLiveData<PassVerification?> = MutableLiveData(null)

    val isReachErrorLimit: Boolean
        get() = PinHelper.isReachErrorLimit

    var wholePinString = ""
        set(value) {
            field = value
            if (value.length == 4) {
                handlePin(field)
            }
        }

    private var singlePinString:String = ""
        set(value) {
            if (value.isEmpty()) return

            if (value == "_") {
                // backspace
                if (wholePinString.isEmpty()) return
                wholePinString = wholePinString.dropLast(1)
                userInputNumber.value = UserInputNumber(wholePinString.length, value)

            }else {
                // 數字
                if (wholePinString.length >= 4) return
                wholePinString += value
                userInputNumber.value = UserInputNumber(wholePinString.length-1, value)

            }
            field = value
        }


    private fun handlePin(pin: String) {
        when(pinInputType) {
            PinInputType.SET_NEW -> setNewPin(pin)

            PinInputType.SET_NEW_CONFIRM -> verifyNewPin(pin, "密碼不相符，請重新輸入")

            PinInputType.ENTER_APP,
            PinInputType.CLOSE_PW_PROTECT,
            PinInputType.MODIFY_PW -> verifyPin(pin, "密碼錯誤，請重新輸入")

            else -> throw IllegalArgumentException("Haven't determined")
        }
    }

    /**
     * 設定新 Pin 碼
     */
    private fun setNewPin(pin: String) {
        viewModelScope.launch {
            delay(CONFIRM_PASSWORD_DELAY_TIME)
            PinHelper.setNewPin(pin)
            // 顯示確認新 Pin 碼的畫面 (SET_NEW -> SET_NEW_CONFIRM)
            changePinInputTypeTo(PinInputType.SET_NEW_CONFIRM)
            clearPin()
        }
    }

    /**
     * 驗證新設定的 Pin 碼
     */
    private fun verifyNewPin(pin: String, errText: String) {
        viewModelScope.launch {
            val isPass = PinHelper.verifyNewPin(pin)
            delay(CONFIRM_PASSWORD_DELAY_TIME)
            isPassVerification.value = PassVerification(isPass, pinInputType)
        }
    }

    /**
     * 驗證 Pin 碼
     */
    private fun verifyPin(pin: String, errText:String) {
        viewModelScope.launch {
            val isPass = PinHelper.verifyPin(pin)
            // 驗證 Pin 碼錯誤
            delay(CONFIRM_PASSWORD_DELAY_TIME)
            isPassVerification.value = PassVerification(isPass, pinInputType)
        }
    }

    fun getErrMsg(): String {
        return PinHelper.tryErrorMsg
    }

    fun clearPin() {
        for (i in 1..wholePinString.length) {
            onClickBtnBack()
        }
    }

    private fun changePinInputTypeTo(pinInputType: PinInputType) {
        this.pinInputType = pinInputType
        this.pinInputTypeLiveData.postValue(pinInputType)
    }

    fun onClickBtnNumber(view: View) {
        if (view is TextView) {
            singlePinString = view.text as String
        }
    }

    fun onClickBtnBack() {
        singlePinString = "_"
    }

    fun onClickResetPin() {
        clearPin()
        if (pinInputType == PinInputType.SET_NEW_CONFIRM) {
            changePinInputTypeTo(PinInputType.SET_NEW)
        }
    }
}

class PinViewModelFactory(private val pinInputType: PinInputType) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(PinInputType::class.java)
            .newInstance(pinInputType)
}

data class UserInputNumber(val index: Int, val number: String)
data class PassVerification(val isPass: Boolean, val currentPinInputType: PinInputType)