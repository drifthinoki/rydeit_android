package com.rydeit.io.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rydeit.io.R
import com.rydeit.io.databinding.ItemPurchasePlanBinding
import java.lang.IndexOutOfBoundsException


class PurchasePlanAdapter(val context: Context, private val selectablePlanList: List<SelectablePlanItem>, val listener: (Int)->Unit): RecyclerView.Adapter<PurchasePlanAdapter.PurchasePlanViewHolder>() {

    inner class PurchasePlanViewHolder(private val binding: ItemPurchasePlanBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: SelectablePlanItem) {
                binding.title.text = item.name
                binding.checkbox.visibility = if (item.isSelected) View.VISIBLE else View.GONE

                if (!item.isSelectable) {
                    binding.title.setTextColor(context.resources.getColor(R.color.neutrals_300))
                    binding.root.setOnClickListener(null)
                }
            }
    }

    private val selectOnlyOneNameItemList by lazy {
        SelectOnlyOneNameItemList(
            selectablePlanList
        )
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasePlanViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemPurchasePlanBinding.inflate(inflater, parent, false)
        val holder = PurchasePlanViewHolder(binding)
        binding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            listener.invoke(position)
            selectOnlyOneNameItemList.selectedAt(position)
            notifyDataSetChanged()
        }
        return holder
    }

    override fun onBindViewHolder(holder: PurchasePlanViewHolder, position: Int) {
        holder.bind(selectOnlyOneNameItemList.getItemAtIndex(position))
    }

    override fun getItemCount(): Int {
        return selectOnlyOneNameItemList.getSize()
    }
}

// 單選
class SelectOnlyOneNameItemList(private val selectablePlanItemList: List<SelectablePlanItem>) {

    fun selectedAt(index: Int) {
        if (index < 0 || index > selectablePlanItemList.size - 1) {
            throw IndexOutOfBoundsException()
        }
        selectablePlanItemList.forEachIndexed { i, selectCurrencyItem ->
            selectCurrencyItem.isSelected = index == i
        }
    }

    fun getItemAtIndex(index: Int): SelectablePlanItem {
        if (index < 0 || index > selectablePlanItemList.size - 1) {
            throw IndexOutOfBoundsException()
        }
        return selectablePlanItemList[index]
    }

    fun getSize():Int {
        return selectablePlanItemList.size
    }
}


class SelectablePlanItem(val name: String, val isSelectable: Boolean, var isSelected: Boolean = false)


