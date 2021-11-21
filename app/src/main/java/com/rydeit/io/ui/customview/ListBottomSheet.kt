package com.rydeit.io.ui.customview

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rydeit.io.ui.MainViewModel
import com.rydeit.io.R
import com.rydeit.io.databinding.FragmentListBottomSheetDialogBinding
import com.rydeit.io.ui.adapter.PurchaseCurrencyAdapter
import com.rydeit.io.ui.adapter.PurchasePlanAdapter

const val ARG_LIST_TYPE = "list_type"

enum class ListBottomSheetType {
    ChoosePlan, ChooseCurrency;

    val title: Int
        get() {
            return when(this){
                ChoosePlan -> R.string.bottom_sheet_title_choose_plan
                ChooseCurrency ->  R.string.bottom_sheet_title_choose_currency
            }
        }
}
/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class ListBottomSheet : BaseBottomSheet(true) {

    companion object {

        val TAG = this::class.java.simpleName

        fun newInstance(listType: ListBottomSheetType): ListBottomSheet =
            ListBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_LIST_TYPE, listType)
                }
            }
    }

    private var _binding: FragmentListBottomSheetDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private val viewModel: PurchaseViewModel by viewModels({requireParentFragment()})
    private val viewModel: MainViewModel by activityViewModels()


    private lateinit var listType:ListBottomSheetType

    interface ChoiceBottomSheetListener {
        fun onSelectItem(listType: ListBottomSheetType, index: Int)
    }

    var choiceBottomSheetListener: ChoiceBottomSheetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listType = it.getSerializable(ARG_LIST_TYPE) as ListBottomSheetType
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.navTopBar.title.text = context?.resources?.getString(listType.title)
        binding.navTopBar.closeButton.setOnClickListener {
            super.dismiss()
        }
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            when(listType) {
                ListBottomSheetType.ChoosePlan -> {
                    adapter = PurchasePlanAdapter(context, viewModel.selectablePlanList) { index ->
                        choiceBottomSheetListener = targetFragment as ChoiceBottomSheetListener
                        choiceBottomSheetListener?.onSelectItem(listType ,index)
                    }
                }
                ListBottomSheetType.ChooseCurrency -> {
                    adapter = PurchaseCurrencyAdapter(
                        context, viewModel.selectableCurrencyList) {
                        choiceBottomSheetListener = targetFragment as ChoiceBottomSheetListener
                        choiceBottomSheetListener?.onSelectItem(listType ,it)
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}