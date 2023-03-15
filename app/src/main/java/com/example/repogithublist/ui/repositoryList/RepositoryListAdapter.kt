package com.example.repogithublist.ui.repositoryList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.repogithublist.databinding.RecyclerviewRepositoryItemBinding
import com.example.repogithublist.domain.model.Repository

class RepositoryListAdapter(private val context: Context, var itemList: ArrayList<Repository>):
    RecyclerView.Adapter<RepositoryListAdapter.RepositoryListViewHolder>() {


    inner class RepositoryListViewHolder(binding: RecyclerviewRepositoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val binding = binding
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        return RepositoryListViewHolder(
            RecyclerviewRepositoryItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        val currentRepo = itemList[position]

        holder.binding.apply {
            nameRepository.text = currentRepo.name
            authorRepository.text = currentRepo.author
            imageView.load(currentRepo.image)
            starCount.text = currentRepo.stargazersCount.toString()
            forkCount.text = currentRepo.forksCount.toString()
        }
    }

    override fun getItemCount() = itemList.size

}