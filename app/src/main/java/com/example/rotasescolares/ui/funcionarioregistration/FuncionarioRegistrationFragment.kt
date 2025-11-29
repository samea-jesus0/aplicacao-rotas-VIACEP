package com.example.rotasescolares.ui.funcionarioregistration

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
import com.example.rotasescolares.databinding.FragmentFuncionarioRegistrationBinding

class FuncionarioRegistrationFragment : Fragment() {

    private var _binding: FragmentFuncionarioRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FuncionarioRegistrationViewModel by viewModels { 
        val funcionarioId = arguments?.getLong("funcionarioId") ?: -1L
        FuncionarioRegistrationViewModelFactory(requireActivity().application, funcionarioId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuncionarioRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        val turnos = resources.getStringArray(R.array.shifts)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, turnos)
        binding.autoCompleteTurnoFuncionario.setAdapter(adapter)

        binding.buttonSaveFuncionario.setOnClickListener {
            saveFuncionario()
        }
    }

    private fun observeViewModel() {
        viewModel.funcionario.observe(viewLifecycleOwner) { funcionario ->
            funcionario?.let {
                binding.editTextNomeFuncionario.setText(it.nome)
                binding.editTextCpfFuncionario.setText(it.cpf)
                binding.editTextTelefoneFuncionario.setText(it.telefone)
                binding.editTextEmailFuncionario.setText(it.email)
                binding.editTextCargoFuncionario.setText(it.cargo)
                binding.autoCompleteTurnoFuncionario.setText(it.turno, false)
            }
        }

        viewModel.funcionarioSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                Toast.makeText(requireContext(), "Funcionário salvo com sucesso!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                viewModel.onFuncionarioSaved()
            }
        }
    }

    private fun saveFuncionario() {
        val nome = binding.editTextNomeFuncionario.text.toString()
        val cpf = binding.editTextCpfFuncionario.text.toString()
        val telefone = binding.editTextTelefoneFuncionario.text.toString()
        val email = binding.editTextEmailFuncionario.text.toString()
        val cargo = binding.editTextCargoFuncionario.text.toString()
        val turno = binding.autoCompleteTurnoFuncionario.text.toString()

        if (nome.isBlank() || cpf.isBlank() || telefone.isBlank() || cargo.isBlank() || turno.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.fill_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveFuncionario(nome, cpf, telefone, email, cargo, turno)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}