package com.example.rotasescolares.ui.classregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.databinding.FragmentClassRegistrationBinding
import com.example.rotasescolares.ui.studentselection.StudentSelectionAdapter

class ClassRegistrationFragment : Fragment() {

    private var _binding: FragmentClassRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClassRegistrationViewModel by viewModels { 
        val classId = arguments?.getLong("classId") ?: -1L
        ClassRegistrationViewModelFactory(requireActivity().application, classId)
    }

    private lateinit var studentSelectionAdapter: StudentSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // Setup Shift Dropdown
        val shifts = resources.getStringArray(R.array.shifts)
        val shiftAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, shifts)
        binding.autoCompleteShift.setAdapter(shiftAdapter)

        // Student Selection RecyclerView
        studentSelectionAdapter = StudentSelectionAdapter()
        binding.recyclerViewStudents.adapter = studentSelectionAdapter

        binding.buttonSaveClass.setOnClickListener {
            saveClass()
        }
    }

    private fun observeViewModel() {
        viewModel.turma.observe(viewLifecycleOwner) { turmaWithAlunos ->
            turmaWithAlunos?.let { 
                binding.editTextClassName.setText(it.turma.name)
                binding.autoCompleteShift.setText(it.turma.shift, false)
                // TODO: Pre-select school and students
            }
        }

        // Setup School Dropdown
        viewModel.allSchools.observe(viewLifecycleOwner) { schools ->
            val schoolNames = schools.map { it.name }
            val schoolAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, schoolNames)
            binding.autoCompleteSchool.setAdapter(schoolAdapter)
        }

        viewModel.allStudents.observe(viewLifecycleOwner) {
            studentSelectionAdapter.submitList(it)
        }

        viewModel.classSaved.observe(viewLifecycleOwner) { classSaved ->
            if (classSaved) {
                Toast.makeText(requireContext(), getString(R.string.class_saved_successfully), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun saveClass() {
        val className = binding.editTextClassName.text.toString()
        val shift = binding.autoCompleteShift.text.toString()
        val schoolName = binding.autoCompleteSchool.text.toString()
        val selectedStudentIds = studentSelectionAdapter.getSelectedStudentIds()

        if (className.isBlank() || shift.isBlank() || schoolName.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveClass(className, shift, schoolName, selectedStudentIds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
