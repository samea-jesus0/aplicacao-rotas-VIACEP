package com.example.rotasescolares.ui.classmanagement

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
import com.example.rotasescolares.data.TurmaWithAlunos
import com.example.rotasescolares.databinding.FragmentClassManagementBinding

class ClassManagementFragment : Fragment(), ClassAdapter.OnItemClickListener {

    private var _binding: FragmentClassManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClassManagementViewModel by viewModels { 
        ClassManagementViewModelFactory(requireActivity().application)
    }
    private lateinit var classAdapter: ClassAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fabAddClass.setOnClickListener {
            findNavController().navigate(R.id.action_classManagementFragment_to_classRegistrationFragment)
        }
    }

    private fun setupRecyclerView() {
        classAdapter = ClassAdapter(this)
        binding.recyclerViewClasses.apply {
            adapter = classAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.allTurmas.observe(viewLifecycleOwner) { turmas ->
            classAdapter.submitList(turmas)
        }
    }

    override fun onEditClick(turmaWithAlunos: TurmaWithAlunos) {
        val bundle = bundleOf("classId" to turmaWithAlunos.turma.turmaId)
        findNavController().navigate(R.id.action_classManagementFragment_to_classRegistrationFragment, bundle)
    }

    override fun onDeleteClick(turmaWithAlunos: TurmaWithAlunos) {
        viewModel.deleteTurma(turmaWithAlunos.turma)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}