package com.example.repogithublist.ui.repositoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.repogithublist.databinding.RecyclerviewRepositoryItemBinding
import com.example.repogithublist.paging.model.Repository

class RepositoryListAdapter():
    PagingDataAdapter<Repository,RepositoryListAdapter.RepositoryListViewHolder>(diffCallback) {

    inner class RepositoryListViewHolder(binding: RecyclerviewRepositoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val binding = binding
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        return RepositoryListViewHolder(
            RecyclerviewRepositoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        val currentRepo = getItem(position)

        holder.binding.apply {
            currentRepo?.run {
                nameRepository.text = name
                authorRepository.text = author
                imageView.load(image)
                starCount.text = stargazersCount.toString()
                forkCount.text = forksCount.toString()
            }

        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }

}