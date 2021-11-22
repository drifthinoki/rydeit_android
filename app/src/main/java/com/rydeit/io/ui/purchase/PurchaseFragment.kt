package com.rydeit.io.ui.purchase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rydeit.io.config.Constants
import com.rydeit.io.databinding.FragmentPurchaseBinding
import com.rydeit.io.ui.MainViewModel
import com.rydeit.io.ui.customview.ListBottomSheet
import com.rydeit.io.ui.customview.ListBottomSheetType
import com.rydeit.io.utils.UiUtil


class PurchaseFragment : Fragment() , ListBottomSheet.ChoiceBottomSheetListener {

    private val TAG = this::class.java.simpleName
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentPurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    // purchase has 4 step
    private var nowAtStep = 1
        set(value) {
            field = value
            if (Constants.DEBUG) Log.e(TAG, "now at step: $value")
        }
    private val endStep = 4

    private val stepViewList by lazy {
        listOf(
            binding.viewPurchaseStep1.root,
            binding.viewPurchaseStep2.root
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListener()
        registerLifeCycleObserver()
        refreshUiByStep(nowAtStep)

        return root
    }

    private fun initListener() {
        binding.viewPurchaseStep1.planTextInputLayout.binding.textInputLayout.setEndIconOnClickListener {

            ListBottomSheet.newInstance(ListBottomSheetType.ChoosePlan)
                .apply { setTargetFragment(this@PurchaseFragment, 300) }
                .show(parentFragmentManager, ListBottomSheet.TAG)
        }

        binding.viewPurchaseStep1.currencyTextInputLayout.binding.textInputLayout.setEndIconOnClickListener {

            ListBottomSheet.newInstance(ListBottomSheetType.ChooseCurrency)
                .apply { setTargetFragment(this@PurchaseFragment, 300) }
                .show(parentFragmentManager, ListBottomSheet.TAG)
        }

        binding.viewPurchaseStep1.amountTextInputLayout.editText.doAfterTextChanged {
            val value = it.toString()
            if (Constants.DEBUG) Log.e(TAG, "user input value: $value" )
            viewModel.updateUserPurchaseItem(amount = value.toInt())
        }

        binding.stepButton.binding.nextButton.setOnClickListener {
            nowAtStep += 1
            if (nowAtStep == endStep) {
                //完成登記
            }
            refreshUiByStep(nowAtStep)
            UiUtil.hideKeyboardFrom(requireContext(), binding.viewPurchaseStep1.amountTextInputLayout.editText)

        }

        binding.stepButton.binding.goBackButton.setOnClickListener {
            nowAtStep -= 1
            refreshUiByStep(nowAtStep)
        }


    }

    private fun refreshUiByStep(step:Int) {
        stepViewList.forEachIndexed { index, linearLayout ->
            if (step == index + 1) {
                linearLayout.visibility = View.VISIBLE
            }else {
                linearLayout.visibility = View.GONE
            }
        }
        binding.stepButton.refreshUI(nowAtStep)
    }

    private fun registerLifeCycleObserver() {
        viewModel.isPurchaseInputValid.observe(viewLifecycleOwner) {
            binding.stepButton.nextButtonEnabled = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearUserPurchaseChoice()
    }

    override fun onSelectItem(listType: ListBottomSheetType, index: Int) {
        when(listType) {
            ListBottomSheetType.ChoosePlan -> {
                val selectablePlan = viewModel.selectablePlanList[index]
                binding.viewPurchaseStep1.planTextInputLayout.binding.editText.setText(selectablePlan.name)
                viewModel.updateUserPurchaseItem(plan = selectablePlan.plan)
            }
            ListBottomSheetType.ChooseCurrency -> {
                val currencyItem = viewModel.selectableCurrencyList[index]
                binding.viewPurchaseStep1.currencyTextInputLayout.binding.editText.hint = null
                binding.viewPurchaseStep1.currencyTextInputLayout.binding.customEdittext.visibility = View.VISIBLE
                binding.viewPurchaseStep1.currencyTextInputLayout.binding.customEdittextIcon.setImageResource(currencyItem.type.icon)
                binding.viewPurchaseStep1.currencyTextInputLayout.binding.customEdittextText.text = currencyItem.type.name
                viewModel.updateUserPurchaseItem(currencyType = currencyItem.type)
            }
        }

    }
}