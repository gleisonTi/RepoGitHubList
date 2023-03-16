package com.example.repogithublist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repogithublist.databinding.ActivityMainBinding
import com.example.repogithublist.ui.repositoryList.RepositoriesViewModel
import com.example.repogithublist.ui.repositoryList.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : RepositoriesViewModel by viewModels()
    private lateinit var repositoryListAdapter: RepositoryListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        setupApp()
    }

    private fun setupApp() {
        lifecycleScope.launch {
            viewModel.repositoryListData.collect {
                repositoryListAdapter.submitData(it)
            }
        }
    }

    private fun setupRecycler() {
        binding.recyclerView.apply {
            repositoryListAdapter = RepositoryListAdapter()
            adapter = repositoryListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }
}