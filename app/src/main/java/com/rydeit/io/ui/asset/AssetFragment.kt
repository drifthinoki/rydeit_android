package com.rydeit.io.ui.asset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.rydeit.io.databinding.FragmentAssetBinding

class AssetFragment : Fragment() {

    private lateinit var assetViewModel: AssetViewModel
    private var _binding: FragmentAssetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        assetViewModel = ViewModelProvider(this).get(AssetViewModel::class.java)

        _binding = FragmentAssetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAsset
        assetViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        return root
    }
}