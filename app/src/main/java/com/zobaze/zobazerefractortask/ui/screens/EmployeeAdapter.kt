package com.zobaze.zobazerefractortask.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zobaze.zobazerefractortask.R
import com.zobaze.zobazerefractortask.databinding.ItemEmployeeBinding
import com.zobaze.zobazerefractortask.domain.model.Employee

/**
 * EmployeeAdapter is created to inflate each user info in sequential manner i.e. vertical listing
 * RecyclerView is used to keep recycling EmployeeViewHolder for better memory optimization.
 */
class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    /**
     * diffUtil is used to listen changes in data and deliver changes to single or group
     * of position so that it will re-inflate only specific item whose data is change.
     *
     * But for this Zobaze task it is not that beneficial because data is not changing here
     */
    private val diffUtil = object : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    /**
     * AsyncListDiffer works well to store data and deliver data changes to recycler view using diff util
     */
    private val items = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemEmployeeBinding.inflate(inflater, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount() = items.currentList.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(items.currentList[position])
    }

    fun setEmployeesList(list: List<Employee>) {
        items.submitList(list)
    }

    /**
     * EmployeeViewHolder works as individual item to inflate each item in recycler view.
     *
     * Multiple instances of EmployeeViewHolder will be create on start but same instances will be re used
     * as user scrolls and data will be changed in same instance of view holder
     */
    inner class EmployeeViewHolder(private val binding: ItemEmployeeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.employee = employee
        }
    }
}