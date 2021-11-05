package com.progressingtoday.rydeit.ui

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputLayout
import com.progressingtoday.rydeit.R
import com.progressingtoday.rydeit.databinding.ViewTextInputLayoutBinding

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, val attrs:AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ViewTextInputLayoutBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTextInputLayoutBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextInputLayoutView,
            0, 0
        )

        binding.textInputTitle.text = attributes.getString(R.styleable.TextInputLayoutView_title)
        binding.textInputEditText.hint = attributes.getString(R.styleable.TextInputLayoutView_hint)
        val inputTypeString = attributes.getString(R.styleable.TextInputLayoutView_inputType)
        print(inputTypeString)
        val inputTypeInt = when(inputTypeString) {
            "textEmailAddress" -> 21
            "textPassword" -> 81
            "phone" -> 3
            else -> throw Resources.NotFoundException()
        }
        binding.textInputEditText.inputType = inputTypeInt

        if (inputTypeInt == 81) {
            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE;
            binding.textInputLayout.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.textfield_show_password, null);


        }

    }
}