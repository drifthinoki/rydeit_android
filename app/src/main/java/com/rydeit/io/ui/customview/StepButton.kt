package com.rydeit.io.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewStepButtonBinding

class StepButton @JvmOverloads constructor(
    context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ViewStepButtonBinding
    var nextButtonEnabled: Boolean = false
        set(value) {
            binding.nextButton.isEnabled = value
            field = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewStepButtonBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StepButton,
            0, 0
        )

        val step = attributes.getInt(R.styleable.StepButton_step, 0)
        refreshUI(step)
    }

    fun refreshUI(step: Int) {
        when(step) {
            0 -> {
                binding.goBackButton.visibility = View.GONE
                binding.nextButton.isEnabled = false
                binding.nextButton.text = context.resources.getString(R.string.next_step_button)
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.progress = 25
            }
            1 -> {
                binding.goBackButton.visibility = View.VISIBLE
                binding.nextButton.isEnabled = true
                binding.nextButton.text = context.resources.getString(R.string.next_step_button)
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.progress = 50
            }
            2 -> {
                binding.goBackButton.visibility = View.VISIBLE
                binding.nextButton.isEnabled = true
                binding.nextButton.text = context.resources.getString(R.string.next_step_button)
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.progress = 75
            }
            3 -> {
                binding.goBackButton.visibility = View.VISIBLE
                binding.nextButton.isEnabled = true
                binding.nextButton.text = context.resources.getString(R.string.finish_register)
                binding.progressBar.visibility = View.GONE
            }

        }
    }

}