package com.example.rotasescolares.ui.guardianmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.R
import com.example.rotasescolares.data.Guardian
import com.example.rotasescolares.databinding.ItemGuardianBinding

class GuardianAdapter(private val listener: OnItemClickListener) : ListAdapter<Guardian, GuardianAdapter.GuardianViewHolder>(GuardianDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianViewHolder {
        val binding = ItemGuardianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuardianViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuardianViewHolder, position: Int) {
        val guardian = getItem(position)
        holder.bind(guardian)
    }

    inner class GuardianViewHolder(private val binding: ItemGuardianBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val guardian = getItem(position)
                    showPopupMenu(view, guardian)
                }
            }
        }

        fun bind(guardian: Guardian) {
            binding.textViewGuardianName.text = guardian.name
            binding.textViewGuardianPhone.text = guardian.phone
            binding.textViewGuardianEmail.text = guardian.email
        }

        private fun showPopupMenu(view: View, guardian: Guardian) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_item_options)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(guardian)
                        true
                    }
                    R.id.action_delete -> {
                        listener.onDeleteClick(guardian)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    interface OnItemClickListener {
        fun onEditClick(guardian: Guardian)
        fun onDeleteClick(guardian: Guardian)
    }

    private class GuardianDiffCallback : DiffUtil.ItemCallback<Guardian>() {
        override fun areItemsTheSame(oldItem: Guardian, newItem: Guardian): Boolean {
            return oldItem.guardianId == newItem.guardianId
        }

        override fun areContentsTheSame(oldItem: Guardian, newItem: Guardian): Boolean {
            return oldItem == newItem
        }
    }
}
