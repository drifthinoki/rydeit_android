package com.rydeit.io.ui.customview

import android.content.Context
import android.content.res.Resources
import android.os.CountDownTimer
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewTextInputLayoutPresentBottomSheetBinding

class CustomTextInputLayoutPresentBottomSheet @JvmOverloads constructor(
    context: Context, val attrs:AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val TAG = this::class.java.simpleName

    var binding: ViewTextInputLayoutPresentBottomSheetBinding
    var onEndIconClick: (()->Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTextInputLayoutPresentBottomSheetBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayoutPresentBottomSheet,
            0, 0
        )

        binding.title.text = attributes.getString(R.styleable.CustomTextInputLayoutPresentBottomSheet_text_field_title)
        binding.editText.hint = attributes.getString(R.styleable.CustomTextInputLayoutPresentBottomSheet_text_field_hint)
        binding.textInputLayout.setEndIconOnClickListener {
            onEndIconClick?.invoke()
        }
    }
}