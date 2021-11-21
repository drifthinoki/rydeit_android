package com.rydeit.io.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rydeit.io.databinding.ItemPurchaseCurrencyBinding
import java.lang.IndexOutOfBoundsException


class PurchaseCurrencyAdapter(val context: Context, private val selectCurrencyItemList: List<SelectCurrencyItem>, val listener: (Int)->Unit): RecyclerView.Adapter<PurchaseCurrencyAdapter.PurchaseCurrencyViewHolder>() {

    inner class PurchaseCurrencyViewHolder(private val binding: ItemPurchaseCurrencyBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: SelectCurrencyItem) {
                val type = item.type
                binding.title.text = type.name
                binding.icon.setImageResource(type.icon)
                binding.checkbox.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            }

    }

    private val selectOnlyOneCurrencyItemList by lazy {
        SelectOnlyOneCurrencyItemList(
            selectCurrencyItemList
        )
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseCurrencyViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemPurchaseCurrencyBinding.inflate(inflater, parent, false)
        val holder = PurchaseCurrencyViewHolder(binding)
        binding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            listener.invoke(position)
            selectOnlyOneCurrencyItemList.selectedAt(position)
            notifyDataSetChanged()
        }
        return holder
    }

    override fun onBindViewHolder(holder: PurchaseCurrencyViewHolder, position: Int) {
        holder.bind(selectOnlyOneCurrencyItemList.getItemAtIndex(position))
    }

    override fun getItemCount(): Int {
        return selectOnlyOneCurrencyItemList.getSize()
    }
}

// 單選
class SelectOnlyOneCurrencyItemList(private val selectCurrencyItemList: List<SelectCurrencyItem>) {

    fun selectedAt(index: Int) {
        if (index < 0 || index > selectCurrencyItemList.size - 1) {
            throw IndexOutOfBoundsException()
        }
        selectCurrencyItemList.forEachIndexed { i, selectCurrencyItem ->
            selectCurrencyItem.isSelected = index == i
        }
    }

    fun getItemAtIndex(index: Int): SelectCurrencyItem {
        if (index < 0 || index > selectCurrencyItemList.size - 1) {
            throw IndexOutOfBoundsException()
        }
        return selectCurrencyItemList[index]
    }

    fun getSize():Int {
        return selectCurrencyItemList.size
    }
}

data class SelectCurrencyItem(val type: CurrencyType, var isSelected: Boolean = false)


