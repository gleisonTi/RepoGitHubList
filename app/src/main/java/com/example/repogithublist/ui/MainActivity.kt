package com.example.repogithublist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var listReposityData = ArrayList<Repository>()
    private var isScrolling : Boolean = false
    private var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupApp()
        setupRecycler()

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    val currentItems = manager.childCount
                    val totalItems = manager.itemCount
                    val scrollOutItems = manager.findFirstVisibleItemPosition()
                    if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                        isScrolling = false
                        page++
                        viewModel.getAllRepositoriesData(page)
                    }
            }
        })
    }

    private fun setupApp() {
        viewModel.getAllRepositoriesData(page)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel._repositoryValues.collect {
                when{
                    it.isLoading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    it.repositoryList.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        listReposityData.addAll(it.repositoryList)
                        repositoryListAdapter.notifyDataSetChanged()
                    }
                    it.error.isNotBlank() -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity,it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.recyclerView.apply {
            repositoryListAdapter = RepositoryListAdapter(this@MainActivity,
                listReposityData)
            adapter = repositoryListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }
}