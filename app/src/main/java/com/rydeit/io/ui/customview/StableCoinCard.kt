package com.rydeit.io.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.rydeit.io.R
import com.rydeit.io.databinding.ViewStableCoinCardBinding

class StableCoinCard @JvmOverloads constructor(
    context: Context, val attrs:AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val TAG = this::class.java.simpleName

    var binding: ViewStableCoinCardBinding
    var eyeIconVisible: Boolean = false
        set(visible) {
            if (visible) {
                binding.stableCoinEyeIcon.setImageResource(R.drawable.ic_password_hide)
                binding.stableCoinAmount.text = context.getString(R.string.invisible)
            } else {
                binding.stableCoinEyeIcon.setImageResource(R.drawable.ic_password_visible)
                binding.stableCoinAmount.text = "\$20.2122"
            }
            field = visible
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewStableCoinCardBinding.inflate(inflater, this, true)

        setAttrs()
    }

    private fun setAttrs() {
        if (attrs == null) return

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyFundCard,
            0, 0
        )

        val type = attributes.getInteger(R.styleable.MyFundCard_my_fund_type, 0)
        configureUI(type)
    }

    private fun configureUI(type: Int) {
        val myFundCardType = MyFundCardType.values()[type]
        binding.stableCoinTitle.text = context.resources.getString(myFundCardType.title)
        binding.stableCoinTitle.setTextColor(context.resources.getColor(myFundCardType.titleTextColor, null))
        binding.stableCoinDescription.text = context.resources.getString(myFundCardType.description)
        binding.stableCoinDescription.setTextColor(context.resources.getColor(myFundCardType.descriptionTextColor, null))
        binding.stableCoinAmount.setTextColor(context.resources.getColor(myFundCardType.amountTextColor, null))
        binding.stableCoinCurrency.setTextColor(context.resources.getColor(myFundCardType.amountTextColor, null))
        binding.stableCoinRoot.background = context.resources.getDrawable(myFundCardType.background, null)
        binding.stableCoinEyeIcon.visibility = if (myFundCardType.hasEyeIcon) View.VISIBLE else View.GONE
        binding.stableCoinEyeIcon.setOnClickListener {
            eyeIconVisible = !eyeIconVisible
        }
    }

}

enum class MyFundCardType {
    BLUE, WHITE;

    val title: Int
        get() {
            return when(this) {
                BLUE -> R.string.stablecoin_revenue_title
                WHITE -> R.string.stablecoin_totol_asset_title
            }
        }

    val titleTextColor: Int
        get() {
            return when (this) {
                BLUE -> R.color.neutrals_100
                WHITE -> R.color.textColorPrimary
            }
        }

    val description: Int
        get() {
            return when(this) {
                BLUE -> R.string.stablecoin_revenue_description
                WHITE -> R.string.stablecoin_totol_asset_description
            }
        }

    val descriptionTextColor: Int
        get() {
            return when (this) {
                BLUE -> R.color.blue_100
                WHITE -> R.color.gb_500
            }
        }

    val amountTextColor: Int
        get() {
            return when (this) {
                BLUE -> R.color.green_300
                WHITE -> R.color.textColorPrimary
            }
        }

    val background: Int
        get() {
            return when(this) {
                BLUE -> R.drawable.blue_400_rec_round
                WHITE -> R.drawable.white_rec_round
            }
        }

    val hasEyeIcon: Boolean
        get() {
            return when(this) {
                BLUE -> false
                WHITE -> true
            }
        }

}