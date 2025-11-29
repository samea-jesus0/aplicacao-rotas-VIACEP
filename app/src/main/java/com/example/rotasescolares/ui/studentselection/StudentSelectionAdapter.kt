package com.example.rotasescolares.ui.studentselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.databinding.ItemStudentSelectionBinding

class StudentSelectionAdapter :
    ListAdapter<Student, StudentSelectionAdapter.StudentViewHolder>(StudentDiffCallback()) {

    private val selectedStudentIds = mutableSetOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding =
            ItemStudentSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student, selectedStudentIds.contains(student.id))
        holder.itemView.setOnClickListener {
            if (selectedStudentIds.contains(student.id)) {
                selectedStudentIds.remove(student.id)
                holder.binding.checkBoxStudent.isChecked = false
            } else {
                selectedStudentIds.add(student.id)
                holder.binding.checkBoxStudent.isChecked = true
            }
        }
    }

    fun getSelectedStudentIds(): List<Long> {
        return selectedStudentIds.toList()
    }

    class StudentViewHolder(val binding: ItemStudentSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student, isSelected: Boolean) {
            binding.textViewStudentName.text = student.fullName
            binding.checkBoxStudent.isChecked = isSelected
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }
}
