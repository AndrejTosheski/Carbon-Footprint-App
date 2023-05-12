package com.example.carbon_footprint_app.ui.tipsandtricks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carbon_footprint_app.ListAdapter
import com.example.carbon_footprint_app.R
import com.example.carbon_footprint_app.databinding.FragmentTipsandtricksBinding

class TipsAndTricksFragment : Fragment() {

    private var _binding: FragmentTipsandtricksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(TipsAndTricksViewModel::class.java)

        _binding = FragmentTipsandtricksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList: List<String> = listOf("Minimize Waste", "Conserve Electricity", "Clean Energy Sources", "Reduce Air Travel", "Consume Mindfully") // Replace with your own list of items

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = ListAdapter(requireContext(), itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.updateList(itemList)
    }
}