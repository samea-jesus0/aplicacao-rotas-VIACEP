package com.example.rotasescolares.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rotasescolares.R
import com.example.rotasescolares.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardTripControl.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_tripControlFragment)
        }

        binding.cardStudents.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_studentManagementFragment)
        }

        binding.cardGuardians.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_guardianManagementFragment)
        }

        binding.cardSchools.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_schoolManagementFragment)
        }

        binding.cardClasses.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_classManagementFragment)
        }

        binding.cardFuncionarios.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_funcionarioManagementFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
