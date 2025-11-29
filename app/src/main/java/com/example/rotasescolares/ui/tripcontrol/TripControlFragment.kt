package com.example.rotasescolares.ui.tripcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rotasescolares.R
import com.example.rotasescolares.data.AppDatabase
import com.example.rotasescolares.databinding.FragmentTripControlBinding

class TripControlFragment : Fragment() {

    private var _binding: FragmentTripControlBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TripControlViewModel by viewModels {
        TripControlViewModelFactory(AppDatabase.getDatabase(requireContext()).studentDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TripControlAdapter {
            viewModel.onStudentClicked(it)
        }

        binding.recyclerViewStudents.adapter = adapter
        binding.recyclerViewStudents.layoutManager = LinearLayoutManager(context)

        viewModel.students.observe(viewLifecycleOwner) {
            adapter.submitList(it.filter { student -> student.status != StudentTripStatus.DROPPED_OFF })
        }

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.buttonMorning.id -> viewModel.setShift(getString(R.string.shift_morning))
                    binding.buttonAfternoon.id -> viewModel.setShift(getString(R.string.shift_afternoon))
                    binding.buttonNight.id -> viewModel.setShift(getString(R.string.shift_night))
                }
            }
        }

        // Set initial shift
        binding.toggleButtonGroup.check(binding.buttonMorning.id)

        binding.buttonResetTrip.setOnClickListener {
            viewModel.resetTrip()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
