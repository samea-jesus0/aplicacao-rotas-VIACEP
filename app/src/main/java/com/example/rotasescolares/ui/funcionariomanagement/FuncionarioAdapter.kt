package com.example.rotasescolares.ui.funcionariomanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.R
import com.example.rotasescolares.data.Funcionario
import com.example.rotasescolares.databinding.ItemFuncionarioBinding

class FuncionarioAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Funcionario, FuncionarioAdapter.FuncionarioViewHolder>(FuncionarioDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuncionarioViewHolder {
        val binding = ItemFuncionarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FuncionarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FuncionarioViewHolder, position: Int) {
        val funcionario = getItem(position)
        holder.bind(funcionario)
    }

    inner class FuncionarioViewHolder(private val binding: ItemFuncionarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val funcionario = getItem(position)
                    showPopupMenu(view, funcionario)
                }
            }
        }

        fun bind(funcionario: Funcionario) {
            binding.textViewFuncionarioName.text = funcionario.nome
            binding.textViewFuncionarioCargo.text = funcionario.cargo
            binding.textViewFuncionarioTurno.text = funcionario.turno
        }

        private fun showPopupMenu(view: View, funcionario: Funcionario) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_item_options)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(funcionario)
                        true
                    }
                    R.id.action_delete -> {
                        listener.onDeleteClick(funcionario)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    interface OnItemClickListener {
        fun onEditClick(funcionario: Funcionario)
        fun onDeleteClick(funcionario: Funcionario)
    }

    private class FuncionarioDiffCallback : DiffUtil.ItemCallback<Funcionario>() {
        override fun areItemsTheSame(oldItem: Funcionario, newItem: Funcionario): Boolean {
            return oldItem.funcionarioId == newItem.funcionarioId
        }

        override fun areContentsTheSame(oldItem: Funcionario, newItem: Funcionario): Boolean {
            return oldItem == newItem
        }
    }
}
