package com.rydeit.io.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewRegisterTopBarBinding

class RegisterTopBar @JvmOverloads constructor(
    context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ViewRegisterTopBarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewRegisterTopBarBinding.inflate(inflater, this, true)

        setAttrs()

    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RegisterTopBar,
            0, 0
        )

        val phase = attributes.getInteger(R.styleable.RegisterTopBar_phase, 1)
        configureUI(phase)
    }

    private fun configureUI(phase: Int) {
        when(phase) {
            1 -> {}
            2 -> {
                binding.leftBar.setBackgroundColor(resources.getColor(R.color.blue_400))
                binding.imageStep2.setImageResource(R.drawable.ic_step_2_toggle)
            }
            3 -> {
                binding.leftBar.setBackgroundColor(resources.getColor(R.color.blue_400))
                binding.rightBar.setBackgroundColor(resources.getColor(R.color.blue_400))
                binding.imageStep2.setImageResource(R.drawable.ic_step_2_toggle)
                binding.imageStep3.setImageResource(R.drawable.ic_step_3_toggle)
            }
        }
    }

}