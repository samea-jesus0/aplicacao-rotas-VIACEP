package com.example.rotasescolares.ui.classmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.R
import com.example.rotasescolares.data.TurmaWithAlunos
import com.example.rotasescolares.databinding.ItemClassBinding

class ClassAdapter(private val listener: OnItemClickListener) : ListAdapter<TurmaWithAlunos, ClassAdapter.ClassViewHolder>(ClassDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val binding = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val turmaWithAlunos = getItem(position)
        holder.bind(turmaWithAlunos)
    }

    inner class ClassViewHolder(private val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val turmaWithAlunos = getItem(position)
                    showPopupMenu(view, turmaWithAlunos)
                }
            }
        }

        fun bind(turmaWithAlunos: TurmaWithAlunos) {
            binding.textViewClassName.text = turmaWithAlunos.turma.name
            binding.textViewClassShift.text = turmaWithAlunos.turma.shift
            binding.textViewStudentCount.text = "${turmaWithAlunos.alunos.size} students"
        }

        private fun showPopupMenu(view: View, turmaWithAlunos: TurmaWithAlunos) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_item_options)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(turmaWithAlunos)
                        true
                    }
                    R.id.action_delete -> {
                        listener.onDeleteClick(turmaWithAlunos)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    interface OnItemClickListener {
        fun onEditClick(turmaWithAlunos: TurmaWithAlunos)
        fun onDeleteClick(turmaWithAlunos: TurmaWithAlunos)
    }

    private class ClassDiffCallback : DiffUtil.ItemCallback<TurmaWithAlunos>() {
        override fun areItemsTheSame(oldItem: TurmaWithAlunos, newItem: TurmaWithAlunos): Boolean {
            return oldItem.turma.turmaId == newItem.turma.turmaId
        }

        override fun areContentsTheSame(oldItem: TurmaWithAlunos, newItem: TurmaWithAlunos): Boolean {
            return oldItem == newItem
        }
    }
}
