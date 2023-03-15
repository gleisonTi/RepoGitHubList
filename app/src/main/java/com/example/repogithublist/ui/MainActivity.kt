package com.example.repogithublist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repogithublist.databinding.ActivityMainBinding
import com.example.repogithublist.domain.model.Repository
import com.example.repogithublist.ui.repositoryList.RepositoriesViewModel
import com.example.repogithublist.ui.repositoryList.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        setupApp()
    }

    private fun setupApp() {
        viewModel.getAllRepositoriesData()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel._repositoryValues.collect {
                when{
                    it.isLoading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    it.repositoryList.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        repositoryListAdapter = RepositoryListAdapter(this@MainActivity,
                            it.repositoryList as ArrayList<Repository>)
                        setupRecycler(repositoryListAdapter)
                    }
                    it.error.isNotBlank() -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity,it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupRecycler(repositoryList: RepositoryListAdapter) {
        binding.recyclerView.apply {
            adapter = repositoryList
            layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.VERTICAL, false)

            setHasFixedSize(true)
        }
    }
}