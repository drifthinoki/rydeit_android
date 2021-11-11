package com.rydeit.io.ui.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewLoginTopBarBinding
import com.rydeit.io.databinding.ViewTextInputLayoutBinding

class LoginTopBar @JvmOverloads constructor(
    context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ViewLoginTopBarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewLoginTopBarBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoginTopBar,
            0, 0
        )

        binding.title.text = attributes.getString(R.styleable.LoginTopBar_login_top_bar_title)
    }

}