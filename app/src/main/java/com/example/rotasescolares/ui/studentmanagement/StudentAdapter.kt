package com.example.rotasescolares.ui.studentmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.R
import com.example.rotasescolares.data.Student
import com.example.rotasescolares.databinding.ItemStudentBinding

class StudentAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val student = getItem(position)
                    showPopupMenu(view, student)
                }
            }
        }

        fun bind(student: Student) {
            binding.textViewStudentName.text = student.fullName
            binding.textViewStudentDob.text = student.dateOfBirth
            binding.textViewStudentAddress.text = "${student.logradouro}, ${student.bairro}, ${student.cidade} - ${student.uf}"
            binding.textViewStudentShift.text = student.shift
        }

        private fun showPopupMenu(view: View, student: Student) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_item_options)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(student)
                        true
                    }
                    R.id.action_delete -> {
                        listener.onDeleteClick(student)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    interface OnItemClickListener {
        fun onEditClick(student: Student)
        fun onDeleteClick(student: Student)
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
