package com.example.rotasescolares.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels { 
        RegistrationViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registrarButton.setOnClickListener {
            val nome = binding.nomeEditText.text.toString()
            val cpf = binding.cpfEditText.text.toString()
            val telefone = binding.telefoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val senha = binding.senhaEditText.text.toString()
            val placaVan = binding.placaVanEditText.text.toString()

            viewModel.registerUser(nome, cpf, telefone, email, placaVan, senha)
        }

        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            if (result) {
                Toast.makeText(requireContext(), "Registro bem-sucedido!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Erro no registro! Verifique os dados ou tente outro email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}