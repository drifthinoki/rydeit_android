package com.progressingtoday.rydeit.ui.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.progressingtoday.rydeit.databinding.FragmentAssetBinding
import com.progressingtoday.rydeit.databinding.FragmentWalletBinding
import com.progressingtoday.rydeit.ui.asset.AssetViewModel

class WalletFragment : Fragment() {

    private lateinit var walletViewModel: WalletViewModel
    private var _binding: FragmentWalletBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textWallet
        walletViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        return root
    }
}