package com.example.rotasescolares.ui.studentmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rotasescolares.R
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.databinding.FragmentStudentManagementBinding

class StudentManagementFragment : Fragment(), StudentAdapter.OnItemClickListener {

    private var _binding: FragmentStudentManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentManagementViewModel by viewModels {
        StudentManagementViewModelFactory(requireActivity().application)
    }
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddStudent.setOnClickListener {
            findNavController().navigate(R.id.action_studentManagementFragment_to_studentRegistrationFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText.orEmpty())
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(this)
        binding.recyclerViewStudents.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allStudents.observe(viewLifecycleOwner) { students ->
            studentAdapter.submitList(students)
        }
    }

    override fun onEditClick(student: Student) {
        val bundle = bundleOf("studentId" to student.id)
        findNavController().navigate(R.id.action_studentManagementFragment_to_studentRegistrationFragment, bundle)
    }

    override fun onDeleteClick(student: Student) {
        viewModel.deleteStudent(student)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}