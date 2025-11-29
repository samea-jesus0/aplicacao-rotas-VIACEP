package com.example.rotasescolares.ui.guardianmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rotasescolares.R
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.data.Guardian
import com.example.rotasescolares.databinding.FragmentGuardianManagementBinding

class GuardianManagementFragment : Fragment(), GuardianAdapter.OnItemClickListener {

    private var _binding: FragmentGuardianManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GuardianManagementViewModel by viewModels { 
        GuardianManagementViewModelFactory(requireActivity().application)
    }
    private lateinit var guardianAdapter: GuardianAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuardianManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddGuardian.setOnClickListener {
            findNavController().navigate(R.id.action_guardianManagementFragment_to_guardianRegistrationFragment)
        }
    }

    private fun setupRecyclerView() {
        guardianAdapter = GuardianAdapter(this)
        binding.recyclerViewGuardians.apply {
            adapter = guardianAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allGuardians.observe(viewLifecycleOwner) { guardians ->
            guardianAdapter.submitList(guardians)
        }
    }

    override fun onEditClick(guardian: Guardian) {
        val bundle = bundleOf("guardianId" to guardian.guardianId)
        findNavController().navigate(R.id.action_guardianManagementFragment_to_guardianRegistrationFragment, bundle)
    }

    override fun onDeleteClick(guardian: Guardian) {
        viewModel.deleteGuardian(guardian)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}