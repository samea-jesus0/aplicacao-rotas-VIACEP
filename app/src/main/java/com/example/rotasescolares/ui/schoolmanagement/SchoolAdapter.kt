package com.example.rotasescolares.ui.schoolmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rotasescolares.R
import com.example.rotasescolares.data.School
import com.example.rotasescolares.databinding.ItemSchoolBinding

class SchoolAdapter(private val listener: OnItemClickListener) : ListAdapter<School, SchoolAdapter.SchoolViewHolder>(SchoolDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = ItemSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = getItem(position)
        holder.bind(school)
    }

    inner class SchoolViewHolder(private val binding: ItemSchoolBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonOptions.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val school = getItem(position)
                    showPopupMenu(view, school)
                }
            }
        }

        fun bind(school: School) {
            binding.textViewSchoolName.text = school.name
            binding.textViewSchoolAddress.text = school.address
            binding.textViewSecretaryPhone.text = school.secretaryPhone
            binding.textViewContactName.text = school.contactName
        }

        private fun showPopupMenu(view: View, school: School) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_item_options)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(school)
                        true
                    }
                    R.id.action_delete -> {
                        listener.onDeleteClick(school)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    interface OnItemClickListener {
        fun onEditClick(school: School)
        fun onDeleteClick(school: School)
    }

    private class SchoolDiffCallback : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.schoolId == newItem.schoolId
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }
    }
}
