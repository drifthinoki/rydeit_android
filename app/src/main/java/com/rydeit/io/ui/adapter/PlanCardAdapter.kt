package com.rydeit.io.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rydeit.io.R
import com.rydeit.io.api.responses.Plan
import com.rydeit.io.databinding.ViewPlanCardBinding
import com.rydeit.io.ui.WebViewActivity

enum class CurrencyType {
    USDT, USDC, ETH;

    val icon: Int
        get() {
            return when(this) {
                USDT -> R.drawable.ic_usdt
                USDC -> R.drawable.ic_usdc
                ETH -> R.drawable.ic_eth
            }
        }
}

class PlanCardAdapter(private val context: Context,
                      private var planList: List<Plan>):
    RecyclerView.Adapter<PlanCardAdapter.PlanCardViewHolder>() {

    inner class PlanCardViewHolder(private val binding: ViewPlanCardBinding)
        : RecyclerView.ViewHolder(binding.root) {
            private val TAG = this::class.java.simpleName
            fun bind(plan: Plan){
                binding.planTitle.text = plan.name
                binding.planDescription.text = plan.description
                binding.planIntroButton.setOnClickListener {
                    val intent = Intent(context, WebViewActivity::class.java)
                    intent.putExtra(KEY_WEB_URL, plan.webUrl)
                    context.startActivity(intent)
                }

                plan.currencyTypeList.forEach {
                    when(it) {
                        CurrencyType.USDT -> binding.iconUsdt.visibility = View.VISIBLE
                        CurrencyType.USDC -> binding.iconUsdc.visibility = View.VISIBLE
                        CurrencyType.ETH -> binding.iconEth.visibility = View.VISIBLE
                    }
                }
                binding.clProgressBar.visibility = if (plan.needProgress) View.VISIBLE else View.GONE
                binding.progressBar.max = plan.progressMax
                binding.progressBar.progress = plan.progressForProgressBar
                binding.progressString.text = plan.progressString
                binding.progressPercent.text = plan.formatProgressPercent



                when(plan.stage) {
                    1 -> binding.tagPurchase.root.visibility = View.VISIBLE
                    2 -> {
                        binding.tagProcessing.root.visibility = View.VISIBLE
                        binding.purchaseButton.isEnabled = false
                        binding.purchaseButton.text = context.getString(R.string.plan_card_button_text_processing)
                    }
                    3 -> {
                        binding.tagComingSoon.root.visibility = View.VISIBLE
                        binding.purchaseButton.isEnabled = false
                    }
                }

                when(plan.rateOfReturnType) {
                    1 -> binding.planReturnTitle.text = context.getString(R.string.rate_of_return)
                    2 -> binding.planReturnTitle.text = context.getString(R.string.performance)
                }

                binding.planReturn.text = plan.rateOfReturn
            }
        }


    companion object {
        val KEY_WEB_URL = "web_url"

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanCardViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ViewPlanCardBinding.inflate(inflater, parent, false)
        return PlanCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanCardViewHolder, position: Int) {
        holder.bind(planList[position])
    }

    override fun getItemCount(): Int {
        return planList.size
    }

    fun updateData(planList: List<Plan>) {
        this.planList = planList
        notifyDataSetChanged()
    }
}