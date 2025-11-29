package com.example.rotasescolares.ui.schoolmanagement

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
import com.example.rotasescolares.data.School
import com.example.rotasescolares.databinding.FragmentSchoolManagementBinding

class SchoolManagementFragment : Fragment(), SchoolAdapter.OnItemClickListener {

    private var _binding: FragmentSchoolManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SchoolManagementViewModel by viewModels { 
        SchoolManagementViewModelFactory(requireActivity().application)
    }
    private lateinit var schoolAdapter: SchoolAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolManagementBinding.inflate(inflater, container, false)
        schoolAdapter = SchoolAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddSchool.setOnClickListener {
            findNavController().navigate(R.id.action_schoolManagementFragment_to_schoolRegistrationFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSchools.apply {
            adapter = schoolAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allSchools.observe(viewLifecycleOwner) { schools ->
            schoolAdapter.submitList(schools)
        }
    }

    override fun onEditClick(school: School) {
        val bundle = bundleOf("schoolId" to school.schoolId)
        findNavController().navigate(R.id.action_schoolManagementFragment_to_schoolRegistrationFragment, bundle)
    }

    override fun onDeleteClick(school: School) {
        viewModel.deleteSchool(school)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}