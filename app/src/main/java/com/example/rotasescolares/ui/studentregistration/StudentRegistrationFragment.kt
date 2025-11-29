package com.example.rotasescolares.ui.studentregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.databinding.FragmentStudentRegistrationBinding

class StudentRegistrationFragment : Fragment() {

    private var _binding: FragmentStudentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentRegistrationViewModel by viewModels { 
        val studentId = arguments?.getLong("studentId") ?: -1L
        StudentRegistrationViewModelFactory(requireActivity().application, studentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        val shifts = resources.getStringArray(R.array.shifts)
        val shiftAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, shifts)
        binding.autoCompleteShift.setAdapter(shiftAdapter)

        binding.editTextCep.addTextChangedListener { cep ->
            if (cep.toString().length == 8) {
                viewModel.buscarCep(cep.toString())
            }
        }

        binding.buttonSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun observeViewModel() {
        viewModel.student.observe(viewLifecycleOwner) { student ->
            student?.let { 
                binding.editTextName.setText(it.fullName)
                binding.editTextDob.setText(it.dateOfBirth)
                binding.editTextCep.setText(it.cep)
                binding.editTextLogradouro.setText(it.logradouro)
                binding.editTextBairro.setText(it.bairro)
                binding.editTextCidade.setText(it.cidade)
                binding.editTextUf.setText(it.uf)
                binding.editTextNotes.setText(it.notes)
                // TODO: Pre-select guardian, school, and shift in dropdowns
            }
        }

        viewModel.viaCepResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                binding.editTextLogradouro.setText(response.logradouro)
                binding.editTextBairro.setText(response.bairro)
                binding.editTextCidade.setText(response.localidade)
                binding.editTextUf.setText(response.uf)
            } else {
                binding.editTextLogradouro.text?.clear()
                binding.editTextBairro.text?.clear()
                binding.editTextCidade.text?.clear()
                binding.editTextUf.text?.clear()
            }
        }

        viewModel.cepError.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.allGuardians.observe(viewLifecycleOwner) { guardians ->
            val guardianNames = guardians.map { it.name }
            val guardianAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, guardianNames)
            binding.autoCompleteGuardian.setAdapter(guardianAdapter)
        }

        viewModel.allSchools.observe(viewLifecycleOwner) { schools ->
            val schoolNames = schools.map { it.name }
            val schoolAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, schoolNames)
            binding.autoCompleteSchool.setAdapter(schoolAdapter)
        }

        viewModel.studentSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                Toast.makeText(requireContext(), getString(R.string.student_saved_successfully), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                viewModel.onStudentSaved()
            }
        }
    }

    private fun saveStudent() {
        val fullName = binding.editTextName.text.toString()
        val dateOfBirth = binding.editTextDob.text.toString()
        val cep = binding.editTextCep.text.toString()
        val logradouro = binding.editTextLogradouro.text.toString()
        val bairro = binding.editTextBairro.text.toString()
        val cidade = binding.editTextCidade.text.toString()
        val uf = binding.editTextUf.text.toString()
        val guardianName = binding.autoCompleteGuardian.text.toString()
        val schoolName = binding.autoCompleteSchool.text.toString()
        val shift = binding.autoCompleteShift.text.toString()
        val notes = binding.editTextNotes.text.toString()

        if (fullName.isBlank() || dateOfBirth.isBlank() || cep.isBlank() || logradouro.isBlank() || bairro.isBlank() || cidade.isBlank() || uf.isBlank() || guardianName.isBlank() || schoolName.isBlank() || shift.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveStudent(fullName, dateOfBirth, cep, logradouro, bairro, cidade, uf, guardianName, schoolName, shift, notes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
