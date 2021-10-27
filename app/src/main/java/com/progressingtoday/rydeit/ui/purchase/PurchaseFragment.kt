package com.progressingtoday.rydeit.ui.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.progressingtoday.rydeit.databinding.FragmentHomeBinding
import com.progressingtoday.rydeit.databinding.FragmentPurchaseBinding
import com.progressingtoday.rydeit.ui.home.HomeViewModel

class PurchaseFragment : Fragment() {

    private lateinit var purchaseViewModel: PurchaseViewModel
    private var _binding: FragmentPurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        purchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPurchase
        purchaseViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        return root
    }
}