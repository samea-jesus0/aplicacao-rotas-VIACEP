package com.example.rotasescolares.ui.guardianregistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.data.Guardian
import com.example.rotasescolares.databinding.FragmentGuardianRegistrationBinding

class GuardianRegistrationFragment : Fragment() {

    private var _binding: FragmentGuardianRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GuardianRegistrationViewModel by viewModels { 
        val guardianId = arguments?.getLong("guardianId") ?: -1L
        GuardianRegistrationViewModelFactory(requireActivity().application, guardianId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuardianRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.guardian.observe(viewLifecycleOwner) { guardian ->
            guardian?.let {
                binding.editTextGuardianName.setText(it.name)
                binding.editTextGuardianCpf.setText(it.cpf)
                binding.editTextGuardianPhone.setText(it.phone)
                binding.editTextGuardianSecondaryPhone.setText(it.secondaryPhone)
                binding.editTextGuardianEmail.setText(it.email)
                binding.editTextGuardianRelationship.setText(it.relationship)
            }
        }

        viewModel.registrationResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), R.string.guardian_saved_successfully, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        binding.buttonSaveGuardian.setOnClickListener {
            saveGuardian()
        }
    }

    private fun saveGuardian() {
        val name = binding.editTextGuardianName.text.toString()
        val cpf = binding.editTextGuardianCpf.text.toString()
        val phone = binding.editTextGuardianPhone.text.toString()
        val secondaryPhone = binding.editTextGuardianSecondaryPhone.text.toString()
        val email = binding.editTextGuardianEmail.text.toString()
        val relationship = binding.editTextGuardianRelationship.text.toString()

        if (name.isBlank() || cpf.isBlank() || phone.isBlank() || relationship.isBlank()) {
            Toast.makeText(requireContext(), R.string.fill_required_fields, Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveGuardian(name, cpf, phone, secondaryPhone, email, relationship)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
