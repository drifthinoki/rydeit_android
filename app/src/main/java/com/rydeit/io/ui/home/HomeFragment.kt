package com.rydeit.io.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rydeit.io.databinding.FragmentHomeBinding
import com.rydeit.io.ui.adapter.PlanCardAdapter

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    lateinit var adapter: PlanCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        registerLifeCycleObserver()

        return root
    }

    private fun initView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PlanCardAdapter(requireContext(), emptyList())

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun registerLifeCycleObserver() {
        viewModel.planList.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        viewModel.isLogin.observe(viewLifecycleOwner) {
            binding.llStableCoin.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}