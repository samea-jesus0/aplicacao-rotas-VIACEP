package com.example.rotasescolares.ui.funcionariomanagement

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
import com.example.rotasescolares.data.Funcionario
import com.example.rotasescolares.databinding.FragmentFuncionarioManagementBinding

class FuncionarioManagementFragment : Fragment(), FuncionarioAdapter.OnItemClickListener {

    private var _binding: FragmentFuncionarioManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FuncionarioManagementViewModel by viewModels { 
        FuncionarioManagementViewModelFactory(requireActivity().application)
    }
    private lateinit var funcionarioAdapter: FuncionarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuncionarioManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddFuncionario.setOnClickListener {
            findNavController().navigate(R.id.action_funcionarioManagementFragment_to_funcionarioRegistrationFragment)
        }
    }

    private fun setupRecyclerView() {
        funcionarioAdapter = FuncionarioAdapter(this)
        binding.recyclerViewFuncionarios.apply {
            adapter = funcionarioAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allFuncionarios.observe(viewLifecycleOwner) { funcionarios ->
            funcionarioAdapter.submitList(funcionarios)
        }
    }

    override fun onEditClick(funcionario: Funcionario) {
        val bundle = bundleOf("funcionarioId" to funcionario.funcionarioId)
        findNavController().navigate(R.id.action_funcionarioManagementFragment_to_funcionarioRegistrationFragment, bundle)
    }

    override fun onDeleteClick(funcionario: Funcionario) {
        viewModel.deleteFuncionario(funcionario)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}