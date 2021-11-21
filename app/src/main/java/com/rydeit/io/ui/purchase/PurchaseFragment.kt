package com.rydeit.io.ui.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rydeit.io.ui.MainViewModel
import com.rydeit.io.databinding.FragmentPurchaseBinding
import com.rydeit.io.ui.customview.ListBottomSheet
import com.rydeit.io.ui.customview.ListBottomSheetType

class PurchaseFragment : Fragment() , ListBottomSheet.ChoiceBottomSheetListener {

    private val TAG = this::class.java.simpleName
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentPurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListener()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearUserPurchaseChoice()
    }

    private fun initListener() {
        binding.planTextInputLayout.binding.textInputLayout.setEndIconOnClickListener {

            ListBottomSheet.newInstance(ListBottomSheetType.ChoosePlan)
                .apply { setTargetFragment(this@PurchaseFragment, 300) }
                .show(parentFragmentManager, ListBottomSheet.TAG)
        }

        binding.currencyTextInputLayout.binding.textInputLayout.setEndIconOnClickListener {

            ListBottomSheet.newInstance(ListBottomSheetType.ChooseCurrency)
                .apply { setTargetFragment(this@PurchaseFragment, 300) }
                .show(parentFragmentManager, ListBottomSheet.TAG)
        }
    }

    override fun onSelectItem(listType: ListBottomSheetType, index: Int) {
        viewModel.updateUserPurchaseItem(listType, index)
        when(listType) {
            ListBottomSheetType.ChoosePlan -> {
                val name = viewModel.selectablePlanList[index].name
                binding.planTextInputLayout.binding.editText.setText(name)
            }
            ListBottomSheetType.ChooseCurrency -> {
                val currencyItem = viewModel.selectableCurrencyList[index]
                binding.currencyTextInputLayout.binding.editText.hint = null
                binding.currencyTextInputLayout.binding.customEdittext.visibility = View.VISIBLE
                binding.currencyTextInputLayout.binding.customEdittextIcon.setImageResource(currencyItem.type.icon)
                binding.currencyTextInputLayout.binding.customEdittextText.text = currencyItem.type.name
            }
        }

    }
}