package com.example.rotasescolares.ui.tripcontrol

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.databinding.ItemTripStudentBinding

class TripControlAdapter(private val onStudentClicked: (Student) -> Unit) :
    ListAdapter<Student, TripControlAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            StudentViewHolder {
        val binding = ItemTripStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
        holder.itemView.setOnClickListener { onStudentClicked(student) }
    }

    inner class StudentViewHolder(private val binding: ItemTripStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.textViewStudentName.text = student.fullName
            binding.textViewAddress.text = "${student.logradouro}, ${student.numero}, ${student.bairro}, ${student.cidade} - ${student.uf}"

            when (student.status) {
                StudentTripStatus.WAITING -> {
                    binding.textViewStatus.text = "Esperando"
                    binding.cardStudent.setCardBackgroundColor(Color.LTGRAY)
                }
                StudentTripStatus.ON_BOARD -> {
                    binding.textViewStatus.text = "Entrou para viagem"
                    binding.cardStudent.setCardBackgroundColor(Color.GREEN)
                }
                StudentTripStatus.DROPPED_OFF -> {
                    //
                }
            }
        }
    }

    private class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }
}
