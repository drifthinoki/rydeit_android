package com.rydeit.io.ui.customview

import android.content.Context
import android.content.res.Resources
import android.os.CountDownTimer
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.rydeit.io.R
import com.rydeit.io.config.Constants
import com.rydeit.io.databinding.ViewTextInputLayoutBinding

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, val attrs:AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val TAG = this::class.java.simpleName

    var binding: ViewTextInputLayoutBinding
    private val buttonTextResList: List<Int> by lazy {
        listOf(
            R.string.send_verify_code, // 傳送驗證碼
            R.string.resend_verify_code // 重新發送
        )
    }
    val buttonTextStringList: List<String> by lazy {
        buttonTextResList.map { context.getString(it) }
    }

    var onButtonClick: (()->Unit)? = null

    private var isInputValid: Boolean = true

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTextInputLayoutBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayout,
            0, 0
        )

        binding.title.text = attributes.getString(R.styleable.CustomTextInputLayout_title)
        binding.editText.hint = attributes.getString(R.styleable.CustomTextInputLayout_hint)
        val inputTypeString = attributes.getString(R.styleable.CustomTextInputLayout_inputType)
        print(inputTypeString)
        val inputTypeInt = when(inputTypeString) {
            "textEmailAddress" -> 21
            "textPassword" -> 81
            "phone" -> 3
            "number" -> 2
            "text" -> 1
            else -> throw Resources.NotFoundException()
        }
        binding.editText.inputType = inputTypeInt
        val buttonText = attributes.getString(R.styleable.CustomTextInputLayout_buttonText)
        buttonText?.let {
            binding.divider.visibility = View.VISIBLE
            binding.editTextButton.visibility = View.VISIBLE
            binding.editTextButton.text = it

            when (it) {
                buttonTextStringList[0],
                buttonTextStringList[1] -> {
                    binding.editTextButton.setOnClickListener {
                        onButtonClick?.invoke()
                        countDown()
                    }
                }
            }
        }

        if (inputTypeInt == 81) {
            binding.editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE;
            binding.textInputLayout.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.textfield_show_password, null);
            binding.textInputLayout.setEndIconTintList(resources.getColorStateList(R.color.password_toggle_color_selector, null))
        }

        binding.editText.doAfterTextChanged {
            if (!isInputValid) {
                isInputValid = !isInputValid
                updateBoxStrokeColor(isInputValid)
            }
        }

    }

    private fun countDown() {
        val button = binding.editTextButton
        button.isEnabled = false

        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
//                Log.e(TAG, "onTick text: ${millisUntilFinished / 1000} 秒")
                button.text = "${millisUntilFinished / 1000} 秒"
            }

            override fun onFinish() {
//                Log.e(TAG, "onFinish text: 重新發送")
                button.isEnabled = true
                button.text = "重新發送"
            }
        }.start()
    }

    fun updateBoxStrokeColor(isInputValid: Boolean) {
        this.isInputValid = isInputValid
        if (!isInputValid) {
            //輸入內容錯誤 顯示紅邊框, 紅底色 UI
            binding.textInputLayout.error = " "
            binding.textInputLayout.errorIconDrawable = null
            binding.textInputLayout.boxBackgroundColor = resources.getColor(R.color.r_100, null)
            binding.textInputLayout.boxStrokeWidth = 2
        }else {
            binding.textInputLayout.error = ""
            binding.textInputLayout.errorIconDrawable = null
            binding.textInputLayout.boxBackgroundColor = resources.getColor(R.color.blue_100, null)
            binding.textInputLayout.boxStrokeWidth = 0
        }
    }
}