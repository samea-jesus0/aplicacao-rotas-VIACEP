package com.example.rotasescolares.ui.schoolregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.databinding.FragmentSchoolRegistrationBinding

class SchoolRegistrationFragment : Fragment() {

    private var _binding: FragmentSchoolRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SchoolRegistrationViewModel by viewModels { 
        val schoolId = arguments?.getLong("schoolId") ?: -1L
        SchoolRegistrationViewModelFactory(requireActivity().application, schoolId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.school.observe(viewLifecycleOwner) { school ->
            school?.let { 
                binding.editTextSchoolName.setText(it.name)
                binding.editTextSchoolAddress.setText(it.address)
                binding.editTextSecretaryPhone.setText(it.secretaryPhone)
                binding.editTextContactName.setText(it.contactName)
            }
        }

        viewModel.schoolSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                Toast.makeText(requireContext(), getString(R.string.school_saved_successfully), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                viewModel.onSchoolSaved()
            }
        }

        binding.buttonSaveSchool.setOnClickListener {
            saveSchool()
        }
    }

    private fun saveSchool() {
        val schoolName = binding.editTextSchoolName.text.toString()
        val schoolAddress = binding.editTextSchoolAddress.text.toString()
        val secretaryPhone = binding.editTextSecretaryPhone.text.toString()
        val contactName = binding.editTextContactName.text.toString()

        if (schoolName.isBlank() || schoolAddress.isBlank() || secretaryPhone.isBlank() || contactName.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveSchool(schoolName, schoolAddress, secretaryPhone, contactName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}