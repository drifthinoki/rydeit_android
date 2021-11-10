package com.rydeit.io.ui.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewTextInputLayoutBinding

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, val attrs:AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    var binding: ViewTextInputLayoutBinding

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
            "text" -> 1
            else -> throw Resources.NotFoundException()
        }
        binding.editText.inputType = inputTypeInt
        val buttonText = attributes.getString(R.styleable.CustomTextInputLayout_buttonText)
        buttonText?.let {
            binding.divider.visibility = View.VISIBLE
            binding.editTextButton.visibility = View.VISIBLE
            binding.editTextButton.text = it
        }

        if (inputTypeInt == 81) {
            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE;
            binding.textInputLayout.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.textfield_show_password, null);


        }

    }
}